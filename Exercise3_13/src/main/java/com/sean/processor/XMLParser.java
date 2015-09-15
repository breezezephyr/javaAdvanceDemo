package com.sean.processor;

import java.io.*;
import java.net.URL;
import java.util.*;

import com.google.common.io.Resources;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class XMLParser {
    protected final static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
    private String resourcePath = System.getProperty("user.dir").concat("\\src\\main\\resources");

    /**
     * 将xml中的占位符，替换为properties文件中的value
     */
    public void XMLParserProcessor() throws Exception {
        Properties properties = PropertiesReader.getProperties();
        URL url = Resources.getResource("object.xml");
        BufferedReader reader = new BufferedReader(new InputStreamReader(
          url.openStream(), "UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(reader);
        Element rootElement = document.getRootElement();
        rootElement = ModifyRootElement(rootElement, properties);
        OutputStream out = null;
        out = new FileOutputStream(resourcePath + "\\object.xml");
        Format format = Format.getCompactFormat();
        format.setIndent("	");
        XMLOutputter outp = new XMLOutputter(format);
        outp.output(document.setRootElement(rootElement), out);
        out.close();

    }

    public Element ModifyRootElement(Element root, Properties properties) {
        List<Element> collection = new ArrayList<Element>();
        root.removeChildren("property");
        for (Map.Entry s : properties.entrySet()) {
            Element element = new Element("property");
            element.setAttribute("name", (String) s.getKey());
            Element child = new Element("value");
            child.addContent((String) s.getValue());
            element.addContent(child);
            collection.add(element);
        }
        root.addContent(collection);
        return root;
    }

    public static void main(String[] args) {
        try {
            new XMLParser().XMLParserProcessor();
        } catch (Exception e) {
            logger.warn("Exception", e);
        }
    }
}
