package com.topideal.dscp.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 协议模板状态枚举
 * @author fengchognshu
 */
public enum AgreementStatusEnum {
    ONE("1","待生效"),
    TWO("2","已生效"),
    THREE("3","已失效");
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

    AgreementStatusEnum(String code, String value) {
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
    public static AgreementStatusEnum getCodeByValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        AgreementStatusEnum[] enumARR= values();
        for(AgreementStatusEnum typeEnum :enumARR){
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
    public static AgreementStatusEnum convertCode(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        AgreementStatusEnum[] enumARR= values();
        for(AgreementStatusEnum typeEnum :enumARR){
            if(typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
