package com.sean.jstack;

import com.google.common.collect.*;
import com.google.common.io.Closer;
import com.google.common.io.LineReader;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * jvm提供了一个jstack的工具，可以把该jvm中运行的线程堆栈导出，具体见j.stack文件
 * DubboServerHandler-192.168.6.96:20880-thread-143为线程的名字，tid为线程id ,java.lang.Thread.State: WAITING,WAITING为线程状态
 * waiting on condition [0x00007f3d67cfa000]表示该线程waiting在tid=0x00007f3d67cfa000的线程上
 * 请写一个程序，解析出线程名，线程id，线程状态，以及哪些线程wait在同一个condition上 。就是说匹配“waiting on condition”的线程进行统计。
 * 输出结果按照等待同一个condition的线程数从大到小排序。
 * Created with IntelliJ IDEA.
 * Author: xiappeng.cai
 * Date: 14-4-25
 * Time: 上午9:56
 */
public class StackInfoProcessor {
    private static Logger logger = LoggerFactory.getLogger(StackInfoProcessor.class);
    private ThreadInfo threadInfo;

    /**
     * 将j.stack文件中有用的两行拿出来
     *
     * @param filename
     * @return
     */
    public List<String[]> getLine(String filename) throws Exception {
        if (filename == null)
            throw new FileNotFoundException();
        Closer closer = Closer.create();
        String[] rawData = { "", "" };
        List<String[]> rawDatas = new ArrayList<String[]>();
        URL url = Resources.getResource(filename);
        try {
            FileReader reader = closer.register(new FileReader(url.getPath()));
            LineReader lineReader = new LineReader(reader);
            String line = "";
            while ((line = lineReader.readLine()) != null) {
                if (line.indexOf("\"") > -1) {          //匹配第一行
                    rawData = new String[2];
                    rawData[0] = line;
                }
                if (line.indexOf("java.lang.Thread.State") > -1) {          //匹配第二行，取当前线程的状态
                    rawData[1] = line.trim().split(" ")[1];
                    rawDatas.add(rawData);
                }
            }
        } finally {
            closer.close();
        }
        return rawDatas;
    }

    /**
     * 清除掉没有处于waiting on condition状态的线程
     *
     * @param rawDatas
     * @return
     */
    public List<String[]> remove(List<String[]> rawDatas) {
        List<String[]> pickedData = Lists.newArrayList();
        for (String[] temp : rawDatas) {
            if (temp[0].lastIndexOf("waiting on condition") > -1)
                pickedData.add(temp);
        }

        return pickedData;
    }

    /**
     * 将原始数据切割后装入ThreadInfo，并以Key=WaitID，Value= ThreadInfo实体放入到Multimap<String, ThreadInfo>集合中
     * ps:Multimap 类似于Map<key,collection>, key:value-> 1:n
     *
     * @param rawDatas
     * @return
     */
    public Multimap<String, ThreadInfo> getThreadInfo(List<String[]> rawDatas) {
        Multimap<String, ThreadInfo> w_IdMap = HashMultimap.create();
        List<ThreadInfo> threadsList = Lists.newArrayList();
        for (String[] rawData : rawDatas) {
            ThreadInfo threadInfo = new ThreadInfo();
            Pattern t_id = Pattern.compile("tid=(0x[\\d\\w]+)");
            Pattern t_name = Pattern.compile("\"([\\d\\D]*)\"");
            Pattern w_Id = Pattern.compile("\\[(0x[\\d\\w]+)\\]");
            Matcher tIdMatcher = t_id.matcher(rawData[0]);
            Matcher nameMatcher = t_name.matcher(rawData[0]);
            Matcher w_IdMatcher = w_Id.matcher(rawData[0]);
            if (tIdMatcher.find()) {
                threadInfo.setThreadId(tIdMatcher.group(1));
            }

            if (nameMatcher.find()) {
                threadInfo.setThreadName(nameMatcher.group(1));
            }

            if (w_IdMatcher.find()) {
                threadInfo.setWaitThreadId(w_IdMatcher.group(1));
            }
            threadInfo.setThreadCondition(rawData[1]);
            w_IdMap.put(threadInfo.getWaitThreadId(), threadInfo);
        }
        return w_IdMap;
    }

    /**
     * 按照对应关系对 aitID:threadInfo-> 1:n  对N从大到小排序
     * 处理MulitMap中的数据，key:value->1:n 取出<key, n>
     * 对n降序排序后，取得序列后的List<Map.Entry<key, n>>
     *
     * @return
     */
    public List<Map.Entry<String, Integer>> getOrderList(Multimap<String, ThreadInfo> w_IdMap) {
        Set<String> keys = w_IdMap.keySet();
        Map<String, Integer> w_IdMappingThread = Maps.newHashMap();
        for (String key : keys) {
            Collection<ThreadInfo> values = w_IdMap.get(key);
            w_IdMappingThread.put(key, values.size());
        }
        List<Map.Entry<String, Integer>> orderList = new ArrayList<Map.Entry<String, Integer>>(w_IdMappingThread.entrySet());
        Collections.sort(orderList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        return orderList;
    }

    /**
     * 按照要求打印结果
     */
    public void printStackInfo(Multimap<String, ThreadInfo> w_IdMap) {
        List<Map.Entry<String, Integer>> orderList = getOrderList(w_IdMap);
        for (Map.Entry<String, Integer> entry : orderList) {
            logger.info(entry.getKey() + "," + entry.getValue());               //输出wait_id 对应个数
            Collection<ThreadInfo> threads = w_IdMap.get(entry.getKey());          //输出wait_id对应的ThreadInfo集合
            for (ThreadInfo thread : threads) {
                logger.info("{}", thread);
            }
            logger.info("");
        }

    }


    public static void main(String[] args) {
        StackInfoProcessor stackInfoProcessor = new StackInfoProcessor();
        List<String[]> dataLines = null; //读文件
        try {
            dataLines = stackInfoProcessor.getLine("j.stack");
        } catch (Exception e) {
            logger.warn("j.stack not found", e);
        }
        dataLines = stackInfoProcessor.remove(dataLines);  //去无效状态
        Multimap<String, ThreadInfo> w_IdMap = stackInfoProcessor.getThreadInfo(dataLines);  //格式化输出
        stackInfoProcessor.printStackInfo(w_IdMap);
    }
}
