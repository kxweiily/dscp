package com.topideal.dscp.dto.request.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 登录 / 注册 / 重置密码 dto
 * @Author: kongxj
 * @Date: 2022/7/13 16:02
 */
@Data
public class LoginReqDto implements Serializable {

    /**
     * 用户账号 （手机/邮箱）
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String rePassword;

    /**
     * 短信/邮箱 验证码
     */
    private String verifyCode;

    /**
     * IDM 验证码 (IDM认证登录专用)
     */
    private String idmCode;

    /**
     * 协议id集合
     */
    private List<String> contractIds;

}
