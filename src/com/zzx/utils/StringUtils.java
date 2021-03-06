package com.zzx.utils;

/**
 * 封装了字符串常用的操作
 */
public class StringUtils {
    /**
     * 将目标字符串首字母变大写
     * @param str 目标字符串
     * @return 首字母大写字符串
     */
    public static String firstCharToUpperCase(String str){

       return str.toUpperCase().substring(0,1)+str.substring(1);
    }
}
