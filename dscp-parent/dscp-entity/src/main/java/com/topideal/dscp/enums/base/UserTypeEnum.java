package com.topideal.dscp.enums.base;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户类型
 * @Author: kongxj
 * @Date: 2022/7/7 16:41
 */
public enum UserTypeEnum {

    BASIC("basic", "普通用户"),

    EMPLOYEE("employee", "企业员工"),

    ADMIN("admin", "企业管理者");

    @EnumValue//标记数据库存的值是code
    private String code;
    @JsonValue
    private String value;


    UserTypeEnum(String code, String value){
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


}
