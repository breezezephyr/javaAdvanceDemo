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
 * grep 命令 Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午10:56
 */
public class GrepCommand extends Command<List<String>> {
    protected final Logger logger = LoggerFactory.getLogger(GrepCommand.class);

    public GrepCommand() {
        super.setCommandName("grep");
    }

    /**
     * 执行Grep命令，主要情况包括 grep ttt 1.txt; *** | grep ttt;
     * 
     * @return List<String>
     * @throws Exception
     */
    public List<String> execute() throws Exception {
        List<String> result = null;
        List<String> tempList = Command.resultList;
        // cat 1.txt | grep ttt 的情况
        if (getArgs().size() == 1) {
            if (tempList.size() == 0) {
                logger.warn("{} {}无效命令", getCommandName(), getArgs());
                throw new IllegalArgumentException(getCommandName().concat(getArgs().toString()));
            } else {
                String grepWord = getArgs().get(0);
                result = Lists.newArrayList();
                for (String line : tempList) {
                    if (line.indexOf(grepWord) > -1) {
                        logger.debug("{}", line);
                        result.add(line);
                    }
                }
            }
        } else if (getArgs().size() == 2) { // grep ttt 1.txt 的情况
            final String grepWord = getArgs().get(0);
            URL url = Resources.getResource(getArgs().get(1));
            result = Resources.readLines(url, Charset.forName("UTF-8"), new LineProcessor<List<String>>() {
                List<String> lines = Lists.newArrayList();

                public boolean processLine(String line) throws IOException {
                    if (line.indexOf(grepWord) > -1) {
                        logger.debug("{}", line);
                        lines.add(line);
                    }
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

        Command.resultList = result;
        return result;
    }
}
