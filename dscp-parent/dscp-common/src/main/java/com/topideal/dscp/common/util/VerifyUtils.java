package com.topideal.dscp.common.util;

import com.topideal.dscp.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author kongxj
 * @version 1.0
 */
public class VerifyUtils {

    // 手机号 正则表达式
    public static String REGEXP_MOBILE = "^((13[0-9])|(14[0|4|5|6|7|8|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$";

    // 邮箱 正则表达式
    public static String REGEXP_MEAIL = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";

    //是否包含中文
    public static String REGEXP_CHARS = "[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]";

    //姓名正则
    public static String REGEXP_NAME = "^([\u4E00-\u9FA5]|[a-zA-Z]){2,10}$";

    //企业名称
    public static String REGEXP_MERCHANT_NAME = "^([\u4E00-\u9FA5]|[a-zA-Z0-9]){2,100}$";

    //全角符号
    public static String ANGLE_OF_SYMBOL = "[^\\x00-\\xff]";

    //全角符号
    public static String IS_FIRST_NUMBER = "^[0-9].*";


    /**
     * 判断手机号码格式是否正确
     *
     * @param mobile
     * @return
     */
    public static Boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) return Boolean.FALSE;

        //新的正则，大陆手机号
        Pattern p = Pattern.compile(REGEXP_MOBILE);
        Matcher m = p.matcher(mobile);
        return m.find();
    }


    /**
     * 判断邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static Boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) return Boolean.FALSE;

        //新的正则，邮箱
        Pattern p = Pattern.compile(REGEXP_MEAIL);
        Matcher m = p.matcher(email);
        return m.find();
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符  false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        if (StringUtils.isBlank(str)) throw new BizException(MegConstants.ERROR_CODE_EMPTY);
        Pattern p = Pattern.compile(REGEXP_CHARS);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 姓名正则
     *
     * @param name 待校验名称
     * @return
     */
    public static boolean isName(String name) {
        if (StringUtils.isBlank(name)) throw new BizException(MegConstants.ERROR_NAME_EMPTY);
        Pattern p = Pattern.compile(REGEXP_NAME);
        Matcher m = p.matcher(name);
        return m.find();
    }

    /**
     * 企业名称正则
     *
     * @param name 待校验名称
     * @return
     */
    public static boolean isMerchantName(String name) {
        if (StringUtils.isBlank(name)) throw new BizException(MegConstants.ERROR_MERCHANT_EMPTY);
        Pattern p = Pattern.compile(REGEXP_MERCHANT_NAME);
        Matcher m = p.matcher(name);
        return m.find();
    }


    /**
     * 判断是否有全角符号
     * @param string
     * @return
     */
    public static boolean isFull(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            String temp = String.valueOf(c);
            // 判断是全角字符
            if (temp.matches(ANGLE_OF_SYMBOL)) {
                return true;
            }
        }
        // 判断首字符是否数字开头
        Pattern p = Pattern.compile(IS_FIRST_NUMBER);
        Matcher m = p.matcher(string);
        return m.find();
    }

}
