package com.sean.utils;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.util.List;

public class DictionaryAccessor {
    public static List<File> getFilesList(String root, String suffix) {
        List<File> files = Lists.newArrayList();
        FluentIterable<File> fluentIterable = Files.fileTreeTraverser().breadthFirstTraversal(new File(root));
        for (File f : fluentIterable) {
            String filePath = f.getAbsolutePath();
            if (f.isFile()) {
                if (!suffix.equals("")) {
                    int begIndex = filePath.lastIndexOf(".");
                    String tempsuffix = "";
                    if (begIndex != -1) {
                        tempsuffix = filePath.substring(begIndex + 1,
                                filePath.length());
                        if (tempsuffix.equals(suffix)) {
                            files.add(f);
                        }
                    }
                }
            }
        }
        return files;
    }
}
