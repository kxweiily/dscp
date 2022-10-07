package com.topideal.dscp.common.constants;

/**
 * 时间 相关 常量
 * @Author: kongxj
 * @Date: 2022/7/27 17:28
 */
public class TimeConstants {

    // token 管理后台过期时间，单位毫秒
    public static final Long TOKEN_EXPIRE_TIME_FOR_CMS_MILLISE = Long.valueOf(3 * 60 * 60 * 1000); // 3个小时

    // token 管理后台过期时间，单位秒
    public static final Long TOKEN_EXPIRE_TIME_FOR_CMS = Long.valueOf(3 * 60 * 60); // 3个小时

    // token app端过期时间，单位毫秒
    public static final Long TOKEN_EXPIRE_TIME_FOR_APP_MILLISE = Long.valueOf(7 * 24 * 60 * 60 * 1000); // 暂定一周

    // token app端过期时间，单位秒
    public static final Long TOKEN_EXPIRE_TIME_FOR_APP = Long.valueOf(7 * 24 * 60 * 60); // 暂定一周

    // 验证码 过期时间，单位秒
    public static final Long VERIFY_CODE_EXPIRE_TIME = Long.valueOf(5 * 60); // 5分钟

    // 验证码 过期时间，单位秒
    public static final Long VERIFY_CODE_NUMBER_LIMIT_TIME = Long.valueOf(60 * 60); // 1小时

}
