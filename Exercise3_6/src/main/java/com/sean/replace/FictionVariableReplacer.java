package com.sean.replace;

import com.google.common.io.Closer;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 神雕文件替换：
 * 现有2个文件，地址在：
 * http://fresh.sean.com/sites/task3.properties
 * http://fresh.sean.com/sites/task3.txt
 * 要求：
 * 根据properties中内容替换掉txt里$function(index)形式文字，将其还原成一本完整小说。
 * 其中function有4种函数，替换规则如下：
 * 1) natureOrder 自然排序，即文本中排列顺序
 * 2) indexOrder 索引排序，文本中每行第一个数字为索引
 * 3) charOrder 文本排序，java的字符排序
 * 4) charOrderDESC 文本倒序，java的字符倒序
 * 注意：txt和properties文件需要程序中现读现解析，而不是下载成本地文件。
 * Created by xiaopeng.cai on 14-4-15.
 */
public class FictionVariableReplacer {
    protected final static Logger logger = LoggerFactory.getLogger(FictionVariableReplacer.class);
    final private static String FictionURL = "http://fresh.sean.com/sites/task3.txt";
    final private static String propertiesURL = "http://fresh.sean.com/sites/task3.properties";

    public void replaceVariable() throws IOException {
        Closer closer = Closer.create();
        try {
            FileReader fReader = new FileReader();
            List<String> content = fReader.getContent(FictionURL);
            Properties indexOrderProperties = getIndexOrder();
            List<String> natureOrderList = getNatureOrder(propertiesURL);
            List<String> charOrderList = getCharOrder(natureOrderList);
            Pattern p = Pattern.compile("\\$(\\w+)\\((\\d+)\\)");
            OutputStreamWriter streamWriter = closer.register(new OutputStreamWriter(
                    new FileOutputStream("result.txt"), "UTF-8"));

            for (String tempStr : content) {
                Matcher m = p.matcher(tempStr);
                while (m.find()) {
                    String orderName = m.group(1);
                    String num = m.group(2);
                    int listSize = natureOrderList.size();
                    if (orderName.equals("natureOrder"))
                        tempStr = tempStr.replace(m.group(), natureOrderList.get(Integer.parseInt(num)));
                    else if (orderName.equals("charOrder"))
                        tempStr = tempStr.replace(m.group(), charOrderList.get(Integer.parseInt(num)));
                    else if (orderName.equals("charOrderDESC"))
                        //倒序为正序下标为：下标位置（集合总数-1）-倒序位置
                        tempStr = tempStr.replace(m.group(), charOrderList.get(listSize - 1 - Integer.parseInt(num)));
                    else if (orderName.equals("indexOrder")) {
                        if (indexOrderProperties.getProperty(num) != null)
                            tempStr = tempStr.replace(m.group(), indexOrderProperties.getProperty(num));
                        else
                            tempStr = tempStr.replace(m.group(), "null");
                    }
                }
                streamWriter.write(tempStr + "\n");
                System.out.println(tempStr);
            }
        } finally {
            closer.close();
        }
    }

    /**
     * 索引序不用处理，直接返回properties类即可
     *
     * @return
     */
    public Properties getIndexOrder() {
        Properties indexProperties = null;
        try {
            indexProperties = new FileReader().getProperties(propertiesURL);
        } catch (IOException e) {
            logger.warn("Properties file read error", e);
        }
        return indexProperties;
    }

    /**
     * 按行读取task3.properties文件
     * 自然序不能从Properties类中取得，Properties类从reader读取资源文件对文件中的Entry按key排序
     */
    public List<String> getNatureOrder(String propertiesURL) {
        List<String> natureOrderList = new ArrayList<String>();
        try {
            URL url = new URL(propertiesURL);
            List<String> content = Resources.readLines(url, Charset.forName("UTF-8"));
            for (String tempStr : content) {
                natureOrderList.add(tempStr.replaceAll("\\d+", "").trim());
            }
        } catch (IOException e) {
            logger.warn("properties file read error", e);
        }
        return natureOrderList;
    }

    /**
     * 字符序将自然序进行排序返回即可
     *
     * @param natureOrderList
     * @return
     */
    public List<String> getCharOrder(List<String> natureOrderList) {
        List<String> charOrderList = new ArrayList<String>(natureOrderList); //重新分配List空间
        Collections.sort(charOrderList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return charOrderList;
    }

    public static void main(String[] args) {
        try {
            new FictionVariableReplacer().replaceVariable();
        } catch (IOException e) {
            logger.warn("result file write error", e);
        }
    }
}
