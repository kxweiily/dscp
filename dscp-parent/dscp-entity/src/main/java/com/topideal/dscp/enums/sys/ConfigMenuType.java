package com.topideal.dscp.enums.sys;

import com.baomidou.mybatisplus.annotation.EnumValue;
import org.apache.commons.lang3.StringUtils;

/**
 * 权限类型
 * @Author: kongxj
 * @Date: 2022/7/19 15:57
 */
public enum ConfigMenuType {

    MENU("menu", "菜单"),

    BUTTON("button", "按钮"),

    DATA("data", "数据");

    @EnumValue//标记数据库存的值是code
    private String code;

    private String value;


    ConfigMenuType(String code, String value){
        this.code = code;
        this.value=value;
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
     * 根据类型名称获取枚举
     * @param code
     * @return
     */
    public static ConfigMenuType getType(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        ConfigMenuType[] enumARR= values();
        for(ConfigMenuType typeEnum :enumARR){
            if(typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }
}
