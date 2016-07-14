package com.demo.java.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    final static String INTEGER_REGEX = "\\d+";
    final static String NUM_REGEX = "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?";

    /**
     * 正则匹配
     *
     * @param str   需要匹配的字符串
     * @param regex 正则
     * @param index 索引
     * @return
     */
    public static String match(String str, String regex, int index) {
        String res = "";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        int i = 0;
        while (matcher.find()) {
            res = matcher.group();
            if (i++ == index) {
                break;
            }
        }
        return res;
    }

    /**
     * 匹配正整数
     *
     * @param str
     * @param index
     * @return
     */
    public static String matchInteger(String str, int index) {
        return match(str, INTEGER_REGEX, index);
    }

    /**
     * 匹配数字类型,包括浮点负数
     *
     * @param str
     * @return
     */
    public static String matchNum(String str) {
        return match(str, NUM_REGEX, 0);
    }
}
