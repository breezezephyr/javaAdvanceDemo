package com.sean.ios;

import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


/**
 * Created by xiaopeng.cai on 14-4-15.
 */
public class PropertiesReader {
    protected final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    public Properties getProperties() {
        URL url = Resources.getResource("env.properties");
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
