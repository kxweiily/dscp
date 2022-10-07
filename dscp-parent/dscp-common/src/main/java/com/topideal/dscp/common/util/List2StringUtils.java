package com.topideal.dscp.common.util;

import java.util.List;

/**
 * @Author: carbon
 * @Date: 2020/6/22 18:35
 * @Version 1.0
 * 将List集合中的数据转换为String的工具类
 */
public class List2StringUtils {

    /**
     * List集合中的数据转换为String
     *
     * @param list
     * @return
     */
    public static String list2String(List<String> list) {
        String result = "";
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                result += (list.get(i) + ",");
            }
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * List集合中的数据拼装为文描的HTML格式的String字符串
     *
     * @param list
     * @return
     */
    public static String list2StringAppend(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            builder.append("<p>");
            for (int i = 0; i < list.size(); i++) {
                builder.append("<img align=\"absmiddle\" src=\"").append(list.get(i)).append("\"/>");
            }
            builder.append("</p>");
            return builder.toString();
        } else {
            return "";
        }
    }
}
