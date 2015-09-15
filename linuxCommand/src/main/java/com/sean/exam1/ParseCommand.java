package com.sean.exam1;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sean.exam1.command.impl.CatCommand;
import com.sean.exam1.command.Command;
import com.sean.exam1.command.impl.GrepCommand;
import com.sean.exam1.command.impl.WcCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 将控制台的命令转化为Command Object Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午11:19
 */
public class ParseCommand {
    protected final Logger logger = LoggerFactory.getLogger(ParseCommand.class);
    private Map<String, Command> commandMap = Maps.newHashMap();

    /**
     * 初始化commandMap
     */
    public ParseCommand() {
        commandMap.put("cat", new CatCommand());
        commandMap.put("grep", new GrepCommand());
        commandMap.put("wc", new WcCommand());
    }

    /**
     * 将命令转化成Command对象
     * 
     * @param commandLine
     * @return
     */
    public LinkedList<Command> getCommands(String commandLine) {
        LinkedList commandList = Lists.newLinkedList();
        Iterable<String> commands = Splitter.on("|").omitEmptyStrings().split(commandLine);
        for (String commandStr : commands) {
            try {
                commandList.add(comandProcessor(commandStr));
            } catch (Exception e) {
                logger.warn("command is not exist", e);
            }

        }
        return commandList;
    }

    /**
     * 处理单个command
     * 
     * @param commandLine
     */
    private Command comandProcessor(String commandLine) throws Exception {
        List<String> commandStrs = Splitter.on(" ").omitEmptyStrings().splitToList(commandLine);
        Command command = commandMap.get(commandStrs.get(0));
        if (command == null) {
            logger.warn("{} is not exist", commandLine);
            throw new IllegalArgumentException(commandLine);
        } else {
            List<String> options = Lists.newArrayList();
            List<String> args = Lists.newArrayList();
            for (int i = 1; i < commandStrs.size(); i++) {
                if (commandStrs.get(i).indexOf("-") > -1) {
                    options.add(commandStrs.get(i));
                } else {
                    args.add(commandStrs.get(i));
                }
            }
            command.setOptions(options);
            command.setArgs(args);
            return command;
        }
    }
}
