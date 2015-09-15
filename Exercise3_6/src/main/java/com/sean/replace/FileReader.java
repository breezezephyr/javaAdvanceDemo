package com.sean.replace;

import com.google.common.io.Closer;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 通过URL对小说，资源文件进行读取
 * Created by xiaopeng.cai on 14-4-15.
 */
public class FileReader {
    protected final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public List<String> getContent(String FictionURL) {
        List<String> content = new ArrayList<String>();
        try {
            URL url = new URL(FictionURL);
            content = Resources.readLines(url, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.warn("novel file read error{}", e);
        }
        return content;
    }

    public Properties getProperties(String propertiesURL) throws IOException {
        Properties properties = new Properties();
        Closer closer = Closer.create();
        try {
            URL url = new URL(propertiesURL);
            URLConnection context = url.openConnection();
            InputStream in = closer.register(context.getInputStream());
            BufferedReader br = closer.register(new BufferedReader(new InputStreamReader(in,
                    "UTF-8")));
            properties.load(br);
        } finally {
            closer.close();
        }
        return properties;
    }
}
