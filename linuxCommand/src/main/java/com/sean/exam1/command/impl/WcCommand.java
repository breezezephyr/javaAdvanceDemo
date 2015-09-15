package com.sean.exam1.command.impl;

import com.google.common.io.Closer;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import com.sean.exam1.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午11:07
 */
public class WcCommand extends Command<Integer> {
    protected final Logger logger = LoggerFactory.getLogger(WcCommand.class);

    public WcCommand() {
        setCommandName("wc");
    }

    /**
     * 执行wc命令 wc -l 1.txt; ***|wc -l;
     * 
     * @return
     * @throws Exception
     */
    public Integer execute() throws Exception {
        List<String> tempList = Command.resultList;
        Integer lineNum = 0;
        if (getOptions().size() == 0) {
            logger.info("please input the option");
            System.exit(0);
        } else if (getOptions().size() == 1) {
            // wc -l 的情况
            if (getOptions().get(0).equals("-l")) {
                if (getArgs().size() == 0) {
                    if (tempList.size() == 0) {
                        logger.warn("{} {}无效命令", getCommandName(), getOptions());
                        throw new IllegalArgumentException(getCommandName().concat(getOptions().toString()));
                    } else {
                        lineNum = tempList.size();
                    }
                } else if (getArgs().size() == 1) { // wc -l 1.txt
                    URL url = Resources.getResource(getArgs().get(0));
                    lineNum = Resources.readLines(url, Charset.forName("UTF-8"), new LineProcessor<Integer>() {
                        Integer lines = 0;

                        public boolean processLine(String line) throws IOException {
                            lines++;
                            return true;
                        }

                        public Integer getResult() {
                            return lines;
                        }
                    });
                }
            } else {
                logger.warn("{} {}无效命令", getCommandName(), getOptions());
                throw new IllegalArgumentException(getCommandName().concat(getOptions().toString()));
            }
        }

        logger.debug("{}", lineNum);
        return lineNum;
    }
}
