package com.topideal.dscp.common.util;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class SignUtils {

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final String  KEY_SIGN = "sign";
    public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String sign(Map<String, String> params, String secretKey) {
        Set<String> paramsKeySet = params.keySet();
        String[] keys = paramsKeySet.toArray(new String[paramsKeySet.size()]);
        Arrays.sort(keys);
        String joinedParams = joinRequestParams(params, secretKey, keys);
        byte[] abstractMessage = digest(joinedParams);
        return byte2Hex(abstractMessage);
    }

    private static String byte2Hex(byte[] data) {
        int length = data.length;
        char hexChars[] = new char[length * 2];
        int index = 0;
        for (byte value : data) {
            hexChars[index++] = HEX_DIGITS[value >>> 4 & 0xf];
            hexChars[index++] = HEX_DIGITS[value & 0xf];
        }
        return new String(hexChars);
    }

    private static byte[] digest(String message) {
        try {
            MessageDigest md5Instance = MessageDigest.getInstance("MD5");
            md5Instance.update(message.getBytes(DEFAULT_CHARSET));
            return md5Instance.digest();
        } catch (Exception e) {
            throw new RuntimeException("签名验证失败", e);
        }
    }

    private static String joinRequestParams(Map<String, String> params, String secretKey, String[] sortedKeys) {

        StringBuilder sb = new StringBuilder(secretKey); // 前面加上secretKey
        for (String key : sortedKeys) {
            if (KEY_SIGN.equals(key)) {
                continue; // 签名时不计算sign本身
            } else {
                String value = params.get(key);
                if (isNotEmpty(key) && isNotEmpty(value)) {
                    sb.append(key).append(value);
                }
            }
        }
        sb.append(secretKey); // 最后加上secretKey
        return sb.toString();
    }

    private static boolean isNotEmpty(String s) {
        return null != s && !"".equals(s);
    }
}