package com.topideal.dscp.common.fieldDES;

/**
 * 数据脱敏类型
 * @Author: kongxj
 * @Date: 2022/8/2 11:27
 */
public enum DataMaskingType {

    /** 无 */
    none,

    /** 密码 */
    password,

    /** 身份证 */
    identityCard,

    /** 手机 */
    telephone,

    /** 邮箱 */
    email,

    /** 姓名 */
    name,

    /** 地址 */
    address,

    /** 图片 */
    picture,

}
