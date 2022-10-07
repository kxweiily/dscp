package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.common.fieldDES.DataMaskingType;
import com.topideal.dscp.common.fieldDES.FieldDES;
import com.topideal.dscp.dto.common.MultitenantDto;
import com.topideal.dscp.enums.base.UserTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 用户表
 *
 * @Author: kongxj
 * @Date: 2022/7/7 16:35
 */
@Data
public class BaseUserRespDto extends MultitenantDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    @FieldDES(dmType = DataMaskingType.password) // 加解密
    private String password;

    /**
     * 手机号码
     */
    @FieldDES(dmType = DataMaskingType.telephone) // 加解密
    private String tel;

    /**
     * 邮箱
     */
    @FieldDES(dmType = DataMaskingType.email) // 加解密
    private String email;

    /**
     * 用户类型
     */
    private UserTypeEnum userType;

    /**
     * 用户头像url
     */
    private String hpUrl;

    /**
     * 是否注销  0 否 1 是
     */
    private Boolean isCancelled;
    /**
     * 注销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cancelledTime;
    /**
     * 商家邀请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date inviteTime;

    /**
     * 商家绑定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bindingTime;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后一次登录ip
     */
    private String lastIp;

    /////
    //    外键字段
    private String merchantName;


    //类型名称
    private String userTypeCode;

}
