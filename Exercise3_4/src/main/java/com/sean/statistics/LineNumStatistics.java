package com.sean.statistics;


import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.io.Closer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sean.utils.DictionaryAccessor;

/**
 * 统计一个Java文件的有效行数。 1) 有效不包括空行 2) 不考虑代码里有多行注释的情况
 * Author: xiappeng.cai
 * Date: 14-4-21
 * Time: 下午5:28
 */
public class LineNumStatistics {
    protected final Logger logger = LoggerFactory
            .getLogger(LineNumStatistics.class);
    private static String pathPrefix = "D:\\LocalGitLib\\fxi\\qfresh_info\\src\\main\\webapp\\js";
    private List<File> file_list;
    static int annotationLine = 0;
    static int blankLine = 0;
    static int totalLine = 0;
    static int fileCount = 0;

    public void codeStatEngine() {
        file_list = DictionaryAccessor.getFilesList(pathPrefix, "js");
        if (file_list.isEmpty()) {
            logger.info("There was no java file in this dictionary!");
            return;
        } else
            for (int i = 0; i < file_list.size(); i++) {
                try {
                    statisticsCode(file_list.get(i).toString());
                } catch (IOException e) {
                    logger.info("file not found", e);
                }
            }
    }

    public void statisticsCode(String file) throws IOException {
        fileCount++;
        BufferedReader reader = null;
        Closer closer = Closer.create();
        // 注释匹配
        Pattern annotationLinePattern = Pattern.compile(
                "((//)|(/\\*+)|((^\\s)*\\*)|((^\\s)*\\*+/))+",
                Pattern.MULTILINE + Pattern.DOTALL);
        // 空白行匹配
        Pattern blankLinePattern = Pattern.compile("^\\s*$");
        // 按行遍历代码文件，进行匹配
        String line = null;
        try {
            reader = closer.register(new BufferedReader(new InputStreamReader(
                    new FileInputStream(file))));
            while ((line = reader.readLine()) != null) {
                if (annotationLinePattern.matcher(line).find()) {
                    annotationLine++;
                }
                if (blankLinePattern.matcher(line).find()) {
                    blankLine++;
                }
                totalLine++;
            }
        } finally {
            closer.close();
        }
    }

    //测试Main方法
    public static void main(String[] args) {
        LineNumStatistics lineNumStatistics = new LineNumStatistics();
        lineNumStatistics.codeStatEngine();
        System.out.println("－－－－－－－－－－统计结果－－－－－－－－－");
        System.out.println(pathPrefix + "文件/目录中文件数量：" + fileCount + "个");
        System.out.println("总行数：" + totalLine);
        System.out.println("注释行数：" + annotationLine);
        System.out.println("空白行数：" + blankLine);
        System.out.println("代码行数：" + (totalLine - annotationLine - blankLine));
    }
}
