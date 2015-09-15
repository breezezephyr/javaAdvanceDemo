package com.sean.handler;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 文件统计：
 * 1) 按照附件第一列分组，输出类似于a ->c f(有去重)
 * 2) 统计每个字母出现的次数
 * 附件内容：
 * b a c
 * a c f
 * b f a
 * c d e
 * a c c
 * d e f
 *
 * @author xiaopeng.cai
 *         2014/4/24
 */
public class DataProcessor {
    protected final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private Map<String, Set<String>> outPutData; //key第一列分组的字母，Value使用Set，可以去重
    private Map<String, Integer> letterCount;   //统计每个字母出现的次数

    /**
     * 按行读取后，处理数据
     */
    public void processData(String filename) {
        outPutData = new TreeMap<String, Set<String>>();
        letterCount = new TreeMap<String, Integer>();
        Set<String> letters;
        try {
            URL url = Resources.getResource(filename);
            List<String> content = Resources.readLines(url, Charset.forName("UTF-8"));
            for (String tempStr : content) {
                String[] chars = tempStr.split(" ");
                if (outPutData.get(chars[0]) != null)
                    letters = outPutData.get(chars[0]);   //如果是同一个分组，将这一组的集合取出来
                else
                    letters = new TreeSet<String>();      //如果没有这个分组，新建一个集合在存数据
                for (int i = 0; i < chars.length; i++) {
                    if (letterCount.get(chars[i]) != null) {
                        letterCount.put(chars[i], letterCount.get(chars[i]) + 1); //统计字母个数
                    } else
                        letterCount.put(chars[i], 1);
                    if (i >= 1)
                        letters.add(chars[i]);            //将同一组的数据添加到集合中
                }
                outPutData.put(chars[0], letters);
            }
        } catch (IOException e) {
            logger.warn("properties file read error", e);
        }
    }

    /**
     * 按要求输出结果
     */
    public void printResult() {
        Iterator iter = outPutData.entrySet().iterator();
        Iterator it = letterCount.entrySet().iterator();
        System.out.println("输出按列分组的结果:");
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + "->" + val);
        }
        System.out.println("输出每个字母出现的次数:");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            System.out.println(key + "->" + val);
        }
    }


    public static void main(String[] args) {
        DataProcessor processor = new DataProcessor();
        processor.processData("input.txt");
        processor.printResult();
    }
}
