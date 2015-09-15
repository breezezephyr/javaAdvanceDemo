package com.sean.mobile.training.ioc.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sean.mobile.training.aop.AopHandler;
import com.sean.mobile.training.ioc.BeanFactory;
import com.sean.mobile.training.utils.AopUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClassPathXmlApplicationContext.class);

    private File file;
    private Map<String, Object> BeanMap = Maps.newHashMap();
    Map<String, String> nameSpaceMap = Maps.newHashMap();
    // value of aop:pointcut attribute expression
    private String pointcutId = "";
    // value of aop:pointcut attribute expression
    private String aopExpression = "";
    // value of aop:aspect attribute ref
    private String aspectRef = "";
    // value of aop:before attribute method
    private String advisorMethod = "";
    // value of aop:before attribute pointcut-ref
    private String pointcutRef = "";

    public ClassPathXmlApplicationContext(String configFile) {
        URL url = this.getClass().getClassLoader().getResource(configFile);
        try {
            file = new File(url.toURI());
            xmlParse();
        } catch (URISyntaxException e) {
            logger.warn("URISyntaxException", e);
        } catch (ClassNotFoundException e) {
            logger.warn("ClassNotFoundException", e);
        } catch (InstantiationException e) {
            logger.warn("InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.warn("IllegalAccessException", e);
        } catch (IOException e) {
            logger.warn("IOException", e);
        } catch (InvocationTargetException e) {
            logger.warn("InvocationTargetException", e);
        } catch (DocumentException e) {
            logger.warn("DocumentException", e);
        }
    }

    private void xmlParse() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException,
            InvocationTargetException, DocumentException {
        SAXReader saxBuilderReader = new SAXReader();
        Document doc = saxBuilderReader.read(file);
        // 读取XML转换成实例化Bean，同时注入依赖，并在BeanMap中注册。
        beanParse(doc);
        // 读取XML中Aop的配置
        aopParse(doc);
        // 通过动态代理的形式，将Advice依赖的对象Weaving到切点中。
        createProxy();
    }

    private void createProxy() {
        List<String> matchedBeanNames = getExpressionMatchedBean(aopExpression);
        for (String beanName : matchedBeanNames) {
            Object bean = BeanMap.get(beanName);
            AopHandler aopHandler = new AopHandler(bean, pointcutRef, aspectRef, advisorMethod, BeanMap);
            Object beanWrapper = Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass()
                    .getInterfaces(), aopHandler);
            BeanMap.put(beanName, beanWrapper);
            //程序有些问题，DI在bean实例的时候已经完成，这里可以通过pointcutId取得我们织入后的对象，方便我们进行测试。
            BeanMap.put(pointcutId, beanWrapper);
        }
    }

    private List<String> getExpressionMatchedBean(String aopExpression) {
        String packagePrefix = AopUtils.getExpPackage(aopExpression);
        List<String> matchBeanList = Lists.newArrayList();
        for (Map.Entry<String, Object> o : BeanMap.entrySet()) {
            String beanName = o.getValue().getClass().getName();
            if (beanName.startsWith(packagePrefix)) {
                matchBeanList.add(o.getKey());
            }
        }
        return matchBeanList;
    }

    private void aopParse(Document doc) {
        nameSpaceMap.put("aop", "http://www.springframework.org/schema/aop");
        XPath configXPath = doc.createXPath("//aop:config");
        configXPath.setNamespaceURIs(nameSpaceMap);
        List<Element> aopConfig = configXPath.selectNodes(doc);
        Iterator<Element> it = aopConfig.iterator();
        while (it.hasNext()) {
            Element aopCfg = it.next();
            XPath pointCutXPath = aopCfg.createXPath("//aop:pointcut");
            List<Element> aopPointCut = pointCutXPath.selectNodes(aopCfg);
            pointcutId = aopPointCut.get(0).attributeValue("id");
            aopExpression = aopPointCut.get(0).attributeValue("expression");
            XPath aopAspectXPath = aopCfg.createXPath("//aop:aspect");
            List<Element> aopAspect = aopAspectXPath.selectNodes(aopCfg);
            aspectRef = aopAspect.get(0).attributeValue("ref");
            XPath advisorXPath = aopAspect.get(0).createXPath("//aop:before");
            List<Element> advisors = advisorXPath.selectNodes(aopAspect.get(0));
            // TODO
            advisorMethod = advisors.get(0).attributeValue("method");
            pointcutRef = advisors.get(0).attributeValue("pointcut-ref");
        }
    }

    private void beanParse(Document doc) throws ClassNotFoundException, IllegalAccessException, InstantiationException,
            InvocationTargetException, DocumentException {
        nameSpaceMap.put("ns", "http://www.springframework.org/schema/beans");
        XPath xPath = doc.createXPath("//ns:bean");
        xPath.setNamespaceURIs(nameSpaceMap);
        List<Element> beans = xPath.selectNodes(doc);
        Iterator it = beans.iterator();
        while (it.hasNext()) {
            Element beanElement = (Element) it.next();
            String id = beanElement.attributeValue("id");
            String cls = beanElement.attributeValue("class");
            Object obj = Class.forName(cls).newInstance();
            Method[] methods = obj.getClass().getDeclaredMethods();
            XPath propertyPath = beanElement.createXPath("ns:property");
            propertyPath.setNamespaceURIs(nameSpaceMap);
            List<Element> list = propertyPath.selectNodes(beanElement);
            for (Element propertyElement : list) {
                for (int n = 0; n < methods.length; n++) {
                    String name = methods[n].getName();
                    String temp = null;
                    if (name.startsWith("set")) {
                        temp = name.substring(3, name.length()).toLowerCase();
                        if (temp.equals(propertyElement.attributeValue("name"))) {
                            methods[n].invoke(obj, propertyElement.attributeValue("value"));
                        } else if (temp.equals(propertyElement.attributeValue("ref"))) {
                            methods[n].invoke(obj, BeanMap.get(propertyElement.attributeValue("ref")));
                        }
                    }
                }
            }
            BeanMap.put(id, obj);
        }
    }

    @Override
    public Object getBean(String name) {
        return BeanMap.get(name);
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        // return (T)classMap.get(clazz); //加入Aop之后，obj经过动态代理后的Class变为java.lang.Proxy${Proxy}无法通过Class匹配
        return (T) BeanMap.get(clazz.getSimpleName().toLowerCase());
    }
}
