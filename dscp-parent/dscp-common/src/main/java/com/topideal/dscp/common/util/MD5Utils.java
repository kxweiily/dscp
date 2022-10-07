package com.topideal.dscp.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.Base64;

/**
 * MD5加密工具类
 * @Author: kongxj
 * @Date: 2020/6/14 16:40
 */
public class MD5Utils {

    /**
     * MD5加密
     * @param text 需要签名的字符串
     * @return 签名结果
     */
    public static String MD5(String text) {
        return DigestUtils.md5Hex(getContentBytes(text, "utf-8"));
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
