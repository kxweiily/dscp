package com.topideal.dscp.common.fieldDES;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 加解密 / 脱敏 字段
 * @Author: kongxj
 * @Date: 2022/7/8 17:50
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
//@JacksonAnnotationsInside
//@JsonSerialize(using = SensitiveJsonSerializer.class)
public @interface FieldDES {

    /**
     * 是否加解密 默认true
     * @return
     */
    boolean isDES() default true;

    /**
     * Data Masking(DM)
     * 是否数据脱敏 默认true
     * @return
     */
    boolean isDM() default true;

    /**
     * 数据脱敏 类型
     * base DataMaskingType
     * @return
     */
    DataMaskingType dmType() default DataMaskingType.none;

}
