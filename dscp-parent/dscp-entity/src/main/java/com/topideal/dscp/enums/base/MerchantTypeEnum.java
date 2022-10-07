package com.topideal.dscp.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 商家类型枚举
 * @author fengchongshu
 */
public enum  MerchantTypeEnum {
    TRADE("1", "大贸"),
    BOND("2", "保税"),
    TRADE_BOND("3", "大贸+保税");
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


    MerchantTypeEnum(String code, String value) {
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
    public static MerchantTypeEnum getCodeByValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        MerchantTypeEnum[] enumARR= values();
        for(MerchantTypeEnum typeEnum :enumARR){
            if(typeEnum.getValue().equals(value)) {
                return typeEnum;
            }
        }
        return null;
    }

    /**
     * code类型转换
     * @param code
     * @return
     */
    public static MerchantTypeEnum convertCode(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        MerchantTypeEnum[] enumARR= values();
        for(MerchantTypeEnum typeEnum :enumARR){
            if(typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
