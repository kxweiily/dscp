package com.topideal.dscp.dto.request.base;

import com.topideal.dscp.common.fieldDES.DataMaskingType;
import com.topideal.dscp.common.fieldDES.FieldDES;
import com.topideal.dscp.enums.base.UserTypeEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户分页查询类
 *
 * @Author: kongxj
 * @Date: 2020/6/17 14:01
 */
@Data
public class BaseUserReqDto implements Serializable {

    /**
     * 用户id
     */
    private String id;

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
     * 注册时间 开始
     */
    private String createTimeStart;

    /**
     * 注册时间 结束
     */
    private Date createTimeEnd;

    /**
     * 注册时间
     */
    @DateTimeFormat  (pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 商家 id
     */
    private String enterpriseId;

    /**
     * 绑定商家时间 开始
     */
    @DateTimeFormat  (pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bindingTimeStart;

    /**
     * 绑定商家时间 结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bindingTimeEnd;


    /*----------------   非数据库字段     ---------------*/

    /**
     * 短信/邮箱 验证码
     */
    private String verifyCode;


}
