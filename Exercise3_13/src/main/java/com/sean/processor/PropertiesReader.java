package com.sean.processor;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Author: xiappeng.cai
 * Date: 14-4-28
 * Time: 上午11:31
 */
public class PropertiesReader {
    protected final static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    /**
     * 读取Properties文件
     *
     * @return
     */
    public static Properties getProperties() {
        URL url = Resources.getResource("object.properties");
        Properties properties = new Properties();
        try {
            InputStream in = url.openStream();
            properties.load(in);
        } catch (IOException e) {
            logger.warn("properties file was not exist", e);
        }
        return properties;
    }
}
