package com.sean.exam1;

import com.sean.exam1.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

/**
 * 执行命令 Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午11:12
 */
public class CommandExecution {
    protected final static Logger logger = LoggerFactory.getLogger(CommandExecution.class);

    /**
     * 执行整条命令
     * 
     * @param commands
     * @return Object
     */

    public Object execute(LinkedList<Command> commands) {
        Object object = null;
        for (Command command : commands) {
            try {
                object = command.execute();
            } catch (Exception e) {
                logger.warn("Exceptions occur{}", e);
            }
        }
        logger.info("result is:{}", object.toString());
        return object;
    }

    public static void main(String[] args) {
        ParseCommand parseCommand = new ParseCommand();
        String cmd = CommandUtils.getConsoleLine();
        LinkedList<Command> commands = parseCommand.getCommands(cmd);
        new CommandExecution().execute(commands);
    }
}
