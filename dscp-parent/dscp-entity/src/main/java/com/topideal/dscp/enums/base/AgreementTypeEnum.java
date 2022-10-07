package com.topideal.dscp.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 协议模板协议类型枚举
 *
 * @author fengchognshu
 */
public enum AgreementTypeEnum {
    PLATFOEM("1", "平台协议"),
    PRIVACY("2", "隐私协议"),
    MERCHANT("3", "商家协议");
    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private String code;

    /**
     * 前台显示值
     */
    @JsonValue
    private String value;

    AgreementTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * value获取对应code
     *
     * @param value
     * @return
     */
    public static AgreementTypeEnum getCodeByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        AgreementTypeEnum[] enumARR = values();
        for (AgreementTypeEnum typeEnum : enumARR) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * string类型code转换enum-code
     *
     * @param code
     * @return
     */
    public static AgreementTypeEnum convertCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        AgreementTypeEnum[] enumARR = values();
        for (AgreementTypeEnum typeEnum : enumARR) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
