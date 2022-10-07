package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.common.fieldDES.DataMaskingType;
import com.topideal.dscp.common.fieldDES.FieldDES;
import com.topideal.dscp.entity.common.MultitenantEntity;
import com.topideal.dscp.enums.base.UserTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 * @Author: kongxj
 * @Date: 2022/7/7 16:35
 */
@Data
@TableName(value = "base_user" , autoResultMap = true)
public class BaseUser extends MultitenantEntity {


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
//    @TableField(typeHandler = DESTypeHandler.class)
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
    private Date cancelledTime;

    /**
     * 商家邀请时间
     */
    private Date inviteTime;
    /**
     * 商家绑定时间
     */
    private Date bindingTime;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后一次登录ip
     */
    private String lastIp;


}
