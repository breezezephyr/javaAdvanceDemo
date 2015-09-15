package com.sean.exam1.command.impl;

import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import com.sean.exam1.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * cat命令 Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午10:54
 */
public class CatCommand extends Command<List<String>> {
    protected final Logger logger = LoggerFactory.getLogger(CatCommand.class);

    public CatCommand() {
        setCommandName("cat");
    }

    /**
     * 执行cat命令
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List<String> execute() throws Exception {
        List<String> result = null;
        if (getOptions().size() == 0) {
            URL url = Resources.getResource(getArgs().get(0));
            result = Resources.readLines(url, Charset.forName("UTF-8"), new LineProcessor<List<String>>() {
                List<String> lines = Lists.newArrayList();

                public boolean processLine(String line) throws IOException {
                    lines.add(line);
                    logger.debug("{}", line);
                    return true;
                }

                public List<String> getResult() {
                    return lines;
                }
            });
        } else {
            logger.warn("{} {}无效命令", getCommandName(), getOptions());
            throw new IllegalArgumentException(getCommandName().concat(getOptions().toString()));
        }

        Command.resultList = result; // 将cat命令后的结果放置到Command全局变量中。
        return result;
    }
}
