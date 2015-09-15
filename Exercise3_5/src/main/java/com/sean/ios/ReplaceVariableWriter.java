package com.sean.ios;

import com.google.common.io.Closer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 写一个程序, 读入 template.txt 和 env.properties
 * 将template 中 ${NAME}表达式里的变量替换为env里设定的值. 然后输出到一个文件里.
 * 第一个变量是: ${webwork.jsp.include_flush}
 * 第二个变量是: ${webwork.i18n.encoding}
 * 第三个第四个变量分别是:　${webwork.ui.templateSuffix}和${webwork.ui.notfound}
 * Created by xiaopeng.cai on 14-4-15.
 */
public class ReplaceVariableWriter {
    protected final static Logger logger = LoggerFactory.getLogger(ReplaceVariableWriter.class);

    public void replaceVariable() throws IOException {
        Closer closer = Closer.create();
        try {
            FileReader fReader = new FileReader();
            List<String> content = fReader.getContent();
            PropertiesReader pReader = new PropertiesReader();
            Properties properties = pReader.getProperties();
            Pattern p = Pattern.compile("\\$\\{(\\w+(?:\\.\\w+)+)\\}");
            OutputStreamWriter streamWriter = closer.register(new OutputStreamWriter(
                    new FileOutputStream("result.txt"), "UTF-8"));
            for (String tempStr : content) {
                Matcher m = p.matcher(tempStr);
                while (m.find()) {
                    if (properties.getProperty(m.group(1)) != null) {
                        tempStr = tempStr.replace(m.group(), properties.getProperty(m.group(1)));
                    } else {
                        tempStr = tempStr.replace(m.group(), "null");
                    }
                }
                streamWriter.write(tempStr + "\n");
                System.out.println(tempStr);
            }
        } finally {
            closer.close();
        }
    }

    public static void main(String[] args) {
        try {
            new ReplaceVariableWriter().replaceVariable();
        } catch (IOException e) {
            logger.warn("Writer file error", e);
        }
    }
}
