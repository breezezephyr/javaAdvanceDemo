package com.sean.exam1.command;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 命令抽象类 Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午10:48
 */
public abstract class Command<T> {
    // 命令的名称
    private String commandName;
    // 命令的选项
    private List<String> options;
    // 命令的参数
    private List<String> args;
    // 子命令执行完成后，将数据缓存到resultList；
    public static List<String> resultList = Lists.newArrayList();

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getCommandName() {

        return commandName;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<String> getArgs() {
        return args;
    }

    public abstract T execute() throws Exception;

}
