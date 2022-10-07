package com.topideal.dscp.common.util;

import com.topideal.dscp.common.exception.BizException;

import java.util.Random;

/**
 * 随机数工具类
 *
 * @author fcs
 */
public class RandomUtils {

    //解决糊糊工具包随机数范围（a-z A-Z 0-9）
    public static final String randomString = "abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 产生随机数
     *
     * @param length 长度
     * @return 随机数
     */
    public static String createRandom(int length) {
        if (length > 0) {
            Random r = new Random();
            StringBuffer number = new StringBuffer();
            for (int i = 0; i < length; i++) {
                //n 取值0--61  str最大下标
                int n = r.nextInt(randomString.length());
                number.append(randomString.substring(n, n + 1));

            }
            return number.toString();
        } else {
            throw new BizException("create random number error,address: RandomUtils-->createRandom,param to" + length);
        }

    }
}
