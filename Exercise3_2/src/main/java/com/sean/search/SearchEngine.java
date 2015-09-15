package com.sean.search;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.io.Closer;
import com.sean.utils.DictionaryAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2、根据指定项目目录下（可以认为是java源文件目录)中，统计被import最多的类。
 *
 * @author xiaopeng.cai
 */
public class SearchEngine {
    protected final static Logger logger = LoggerFactory.getLogger(SearchEngine.class);
    public Map<String, Integer> importClasses;
    public static String pathPrefix = "TestData";
    public List<File> files;

    /*
     * 对每一个Java文件按行读取，查找包含import的文本，并对文本进行格式化
     */
    public Map<String, Integer> getImportClass() throws IOException {
        importClasses = new HashMap<String, Integer>();
        files = DictionaryAccessor.getFilesList(pathPrefix, "java");
        Closer closer = Closer.create();
        if (files.isEmpty()) {
            logger.warn("There was no java file in this dictionary!");
            return Collections.EMPTY_MAP;
        } else {
            InputStream in = null;
            BufferedReader reader = null;
            for (File file : files) {
                try {
                    in = closer.register(new FileInputStream(file));
                    reader = closer.register(new BufferedReader(
                            new InputStreamReader(in, "UTF-8")));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        if (line.indexOf("import") > -1) {
                            line = line.substring(6, line.lastIndexOf(";"))
                                    .trim();
                            if (importClasses.get(line) == null) {
                                importClasses.put(line, 1);
                            } else {
                                importClasses.put(line,
                                        importClasses.get(line) + 1);
                            }
                        }
                    }
                } finally {
                    closer.close();
                }
            }
        }
        return importClasses;
    }

    /**
     * 内部类，按照HashMap中的值的大小进行排序
     *
     * @author xiaopeng.cai
     */
    private static class ValueComparator implements
            Comparator<Map.Entry<String, Integer>> {
        public int compare(Map.Entry<String, Integer> m,
                           Map.Entry<String, Integer> n) {
            return n.getValue() - m.getValue();
        }
    }

    public static void main(String[] args) {
        SearchEngine engine = new SearchEngine();
        Map<String, Integer> map = null;
        try {
            map = engine.getImportClass();
        } catch (IOException e) {
            logger.warn("read file exception", e);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList();
        if (map.size() != 0) {
            list.addAll(map.entrySet());
            ValueComparator vc = new ValueComparator();
            Collections.sort(list, vc);
            Iterator<Map.Entry<String, Integer>> it = list.iterator();
            System.out.println("在" + pathPrefix + "文档中，java文件import最多的类是："
                    + it.next());
        } else
            System.out.println("目录中没有文件");

    }
}
