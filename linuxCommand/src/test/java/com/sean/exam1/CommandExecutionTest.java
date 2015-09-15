package com.sean.exam1;

import com.sean.exam1.command.Command;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午11:12
 */
public class CommandExecutionTest {
    private ParseCommand parseCommand;

    @Before
    public void setUp() throws Exception {
        parseCommand = new ParseCommand();

    }

    @Test
    public void testCatExecute_1() throws Exception {

        LinkedList<Command> commands = parseCommand.getCommands("cat 1.txt");
        Object obj = new CommandExecution().execute(commands);
        assertNotNull(obj.toString());
    }

    @Test
    public void testCatExecute_2() throws Exception {

        LinkedList<Command> commands = parseCommand.getCommands("cat 1.txt |grep ttt");
        Object obj = new CommandExecution().execute(commands);
        assertTrue(obj.toString().contains("ttt"));
    }

    @Test
    public void testExecute() throws Exception {
        LinkedList<Command> commands = parseCommand.getCommands("cat 1.txt | grep ttt | wc -l");
        Object obj = new CommandExecution().execute(commands);
        assertTrue(obj.toString().contains("3"));
    }

    @Test
    public void testGrepExecute_2() throws Exception {
        LinkedList<Command> commands = parseCommand.getCommands("grep ttt 1.txt");
        Object obj = new CommandExecution().execute(commands);
        assertTrue(obj.toString().contains("ttt"));
    }

    @Test
    public void testWcExecute_1() throws Exception {
        LinkedList<Command> commands = parseCommand.getCommands("wc -l 1.txt");
        Object obj = new CommandExecution().execute(commands);
        assertNotNull(obj.toString().contains("7"));
    }
}
