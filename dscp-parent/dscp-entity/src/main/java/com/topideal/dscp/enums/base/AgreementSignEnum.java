package com.topideal.dscp.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 协议模板签约端枚举
 * @author fengchognshu
 */
public enum AgreementSignEnum {
    AND("1","APP(Android)"),
    IOS("2","APP(IOS)"),
    PC("3","PC端");

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

    AgreementSignEnum(String code, String value) {
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
     * @param value
     * @return
     */
    public static AgreementSignEnum getCodeByValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        AgreementSignEnum[] enumARR= values();
        for(AgreementSignEnum typeEnum :enumARR){
            if(typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * string类型code转换enum-code
     * @param code
     * @return
     */
    public static AgreementSignEnum convertCode(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        AgreementSignEnum[] enumARR= values();
        for(AgreementSignEnum typeEnum :enumARR){
            if(typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
