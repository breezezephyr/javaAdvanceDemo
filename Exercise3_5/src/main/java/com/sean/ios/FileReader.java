package com.sean.ios;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaopeng.cai on 14-4-15.
 */
public class FileReader {
    protected final Logger logger = LoggerFactory.getLogger(FileReader.class);

    public List<String> getContent() {
        URL url = Resources.getResource("template.txt");
        List<String> content = new ArrayList<String>();
        try {
            content = Resources.readLines(url, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.warn("template file was not exist", e);
        }
        return content;
    }
}
