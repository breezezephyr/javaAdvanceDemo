package com.sean.exam1;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.sean.exam1.command.Command;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午11:12
 */
public class CommandExecutionErrorTest {
    private ParseCommand parseCommand;

    @Before
    public void setUp() throws Exception {
        parseCommand = new ParseCommand();

    }

    @Test
    public void testCatExecute_3() {
        LinkedList<Command> commands = parseCommand.getCommands("cat -h 1.txt");
        try {
            new CommandExecution().execute(commands);
        } catch (Exception e) {
            String log = e.toString();
            assertTrue(log.contains("cat[-h]"));
        }
    }

    @Test
    public void testGrepExecute_1() {
        LinkedList<Command> commands = parseCommand.getCommands("grep ttt");
        try {
            new CommandExecution().execute(commands);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("grep[ttt]"));
        }
    }

    @Test
    public void testWcExecute_2() {
        LinkedList<Command> commands = parseCommand.getCommands("wc -l");
        try {
            new CommandExecution().execute(commands);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("wc[-l]"));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testExecute_6() {
        LinkedList<Command> commands = parseCommand.getCommands("ls -a");
        new CommandExecution().execute(commands);

    }
}
