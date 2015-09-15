package com.sean.statistics;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sean.utils.DictionaryAccessor;

/**
 * 3、统计： 1) 查找一个目录下，所有文件中数字、字母(大小写不区分)、汉字、空格的个数、行数。 2) 将结果数据写入到文件中。 文件格式如下：
 * 数字：198213个 字母：18231个 汉字：1238123个 空格：823145个 行数：99812行
 * <p/>
 * <p/>
 * 数字0：123个 数字1：1214个 数字2：23423个 …… 字母A：754456个 数字B：7567个 数字C：456456个 ......
 *
 * @author xiaopeng.cai
 */
public class StatisticsEngine {
    protected final static Logger logger = LoggerFactory
            .getLogger(StatisticsEngine.class);
    private static String pathPrefix = "TestData";
    private List<File> file_list;
    private int digtNum = 0;
    private int[] digtsNum = new int[10];
    private int alphaNum = 0;
    private int[] alphasNum = new int[26];
    private int zhNum = 0;
    private int blankNum = 0;
    private int lineNum = 0;

    public void statisticChar() {
        file_list = DictionaryAccessor.getFilesList(pathPrefix, "txt");
        if (file_list.isEmpty()) {
            logger.info("There was no txt file in this dictionary!");
            return;
        } else
            for (File f : file_list) {
                try {
                    searchFile(f);
                } catch (IOException e) {
                    logger.warn("文件读写有误", e);
                }
            }
    }

    public void searchFile(File file) throws IOException {
        InputStream stream = null;
        BufferedReader reader = null;
        Closer closer = Closer.create();
        try {
            stream = closer.register(new FileInputStream(file));
            reader = closer.register(new BufferedReader(new InputStreamReader(stream, "UTF-8")));
            boolean isEmpty = true;
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                isEmpty = false;
                if (ch >= '0' && ch <= '9') {
                    digtNum++;
                    digtsNum[ch - '0']++;
                }
                if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                    alphaNum++;
                    if (ch >= 'A' && ch <= 'Z') {
                        alphasNum[ch - 'A']++;
                    } else {
                        alphasNum[ch - 'a']++;
                    }
                }
                if (ch >= 0x4E00 && ch <= 0x9FA5) {
                    zhNum++;
                }
                if (ch == ' ') {
                    blankNum++;
                }
                if (ch == '\n') {
                    lineNum++;
                }
            }
            if (!isEmpty)
                lineNum++;
        } finally {
            closer.close();
        }
    }

    public void saveToFile(String fileName, String encoding) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedWriter fileWriter = null;
        try {
            fileWriter = Files.newWriter(new File(fileName), Charset.forName(encoding));
            fileWriter.write("数字：" + digtNum + "个\n");
            fileWriter.write("字母：" + alphaNum + "个\n");
            fileWriter.write("数字：" + digtNum + "个\n");
            fileWriter.write("字母：" + alphaNum + "个\n");
            fileWriter.write("汉字：" + zhNum + "个\n");
            fileWriter.write("空格：" + blankNum + "个\n");
            fileWriter.write("行数：" + lineNum + "个\n");
            fileWriter.write("\n");

            for (int i = 0; i < 10; i++) {
                fileWriter.write("数字" + i + '：' + digtsNum[i] + "个\n");
            }
            fileWriter.write("\n");
            for (int j = 0; j < 26; j++) {
                fileWriter.write("字母" + (char) ('A' + j) + '：' + alphasNum[j]
                        + "个\n");
            }

        } finally {
            fileWriter.close();
        }

    }

    public static void main(String[] args) {
        StatisticsEngine engine = new StatisticsEngine();
        engine.statisticChar();
        try {
            engine.saveToFile("out.txt", "UTF-8");
        } catch (IOException e) {
            logger.warn("文件读写有误", e);
        }

    }
}
