package com.sean.processor;

import com.google.common.io.Resources;
import com.sean.fresh.User;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 将xml解析成对象，调用getter方法的时候可以获得值
 * Author: xiappeng.cai
 * Date: 14-4-30
 * Time: 上午11:20
 */
public class ObjectParser {
    protected final static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    public Object xmlParserToObject() {
        Object o = null;
        try {
            URL url = Resources.getResource("object.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
              url.openStream(), "UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(reader);
            Element rootElement = document.getRootElement();
            o = getObject(rootElement);
        } catch (IOException e) {
            logger.warn("Read XML file error", e);
        } catch (JDOMException e) {
            logger.warn("Jdom error", e);
        }
        return o;
    }

    /**
     * 通过反射将properties的值set到Java Object
     *
     * @param rootElement
     * @return
     */
    public Object getObject(Element rootElement) {
        List<Element> elements = rootElement.getChildren("property");
        String className = rootElement.getAttributeValue("class");
        Object object = null;
        try {
            Class clazz = Class.forName(className);
            object = clazz.newInstance();
            for (Element e : elements) {
                Field field = clazz.getDeclaredField(e.getAttributeValue("name"));
                field.setAccessible(true);
                if (field.getGenericType().equals(int.class)) {
                    field.set(object, Integer.parseInt(e.getChild("value").getValue()));
                } else if (field.getGenericType().equals(Date.class)) {
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = format.parse(e.getChild("value").getValue());
                    field.set(object, d1);
                } else {
                    field.set(object, e.getChild("value").getValue());
                }
            }
        } catch (ClassNotFoundException e) {
            logger.warn("Class Not Found ", e);
        } catch (InstantiationException e) {
            logger.warn("", e);
        } catch (IllegalAccessException e) {
            logger.warn("", e);
        } catch (NoSuchFieldException e) {
            logger.warn("Field not found", e);
        } catch (ParseException e) {
            logger.warn("parse error", e);
        }
        return object;
    }

    public static void main(String[] args) {
        ObjectParser objectParser = new ObjectParser();
        try {
            User user = (User) objectParser.xmlParserToObject();
            System.out.print(user);
        } catch (Exception e) {
            logger.warn("parse error", e);
        }
    }
}
