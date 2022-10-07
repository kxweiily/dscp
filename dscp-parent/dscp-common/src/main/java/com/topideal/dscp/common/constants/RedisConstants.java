package com.topideal.dscp.common.constants;

/**
 * Redis 常量类
 *
 * @Author: kongxj
 * @Date: 2020/11/24 16:03
 */
public class RedisConstants {

    /**
     * 管理后台cms 存放用户tokenMap 分组
     */
    public static final String CMS_JWT_TOKEN = "cms_jwt_token:";

    /**
     * app端 存在用户tokenMap 分组
     */
    public static final String APP_JWT_TOKEN = "app_jwt_token:";

    /**
     * 存放用户tokenMap 中的 token key
     */
    public static final String JWT_TOKEN_TOKEN_KEY = "token";

    /**
     * 存放用户tokenMap 中的 权限 key
     */
    public static final String JWT_TOKEN_GRANTEDAUTHORITIES_KEY = "grantedAuthorities";

    /**
     * 手机 / 邮箱 验证码
     */
    public static final String SMS_VERIFY_CODE = "sms_verify_code:";

    /**
     * 记录用户 手机 / 邮箱 验证码发送次数
     */
    public static final String SMS_VERIFY_CODE_SENDNUM_LIMIT = "sms_verify_code_sendnum_limit:";


}
