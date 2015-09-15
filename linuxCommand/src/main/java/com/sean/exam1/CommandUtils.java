package com.sean.exam1;

import com.google.common.base.Splitter;
import com.sean.exam1.command.Command;

import java.util.Map;
import java.util.Scanner;

/**
 * Utils类 从console读取命令
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-12 Time: 上午10:29
 */
public class CommandUtils {
    public static String getConsoleLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("please input the command:");
        return scanner.nextLine();
    }
}
