package com.sean.mobile.training.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA. Author: xiappeng.cai Date: 14-5-22 Time: 下午8:06
 */
public class AopUtils {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(".+\\(.+ (.+)\\.\\*.(.+)\\(.+\\)\\)");

    public static String getExpPackage(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public static String getExpMethod(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (matcher.find()) {
            return matcher.group(2);
        } else {
            return "";
        }
    }
}
