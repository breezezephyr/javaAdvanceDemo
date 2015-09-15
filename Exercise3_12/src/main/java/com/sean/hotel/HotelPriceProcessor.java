package com.sean.hotel;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: xiappeng.cai Date: 14-4-28 Time: 下午4:58
 */
public class HotelPriceProcessor {
    private static final Logger logger = LoggerFactory.getLogger(HotelPriceProcessor.class);

    public static void main(String[] args) {
        String s = "erere=酒店";
        String testString = "foo , what,,,more,";
        System.out.println(CharMatcher.is('=').or(CharMatcher.JAVA_LOWER_CASE).removeFrom(s));
        // System.out.println(Splitter.on("=").trimResults(CharMatcher.is('=')).split(s));
        // Iterable<String> split = Splitter.on("=").omitEmptyStrings().trimResults().split(s);
        // System.out.println(split);
        String requestBody = "city=上海&name=酒店&address=南京路&brand=7天,如家&star=3,4&price=100-200,300-500&area=中关村&sort=price&order=sort&page=";
        String[] parameters = requestBody.split("&");
        Map requestParameter = Maps.newHashMap();
        // logger.info("ency{},enc{},","df","fd","fdf");
        // for (String parameter : parameters) {
        // parameter = parameter.trim();
        // Pattern pattern = Pattern.compile("(\\w+)=(.+)");
        // Matcher matcher = pattern.matcher(parameter);
        // if (matcher.find()) {
        // requestParameter.put(matcher.group(1), matcher.group(2));
        // }
        // }
        RangeSet rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed("100", "200"));
//        rangeSet.add(Range.closed(200, 500));
//        rangeSet.add(Range.closed(700, 899));
//        System.out.println(rangeSet.contains("125"));

        // System.out.println(rangeSet);
        System.out.println(Splitter.on(",").split(""));
    }

}
