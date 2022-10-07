package com.topideal.dscp.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

/**
 * 系统运营用户
 *
 * @Author: lizhenni
 * @Date: 2020/6/12 11:30
 */
@Data
@TableName(value = "sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 用户名
     */
    private String name;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用  0 禁用 1 启用
     */
    private Boolean isEnabled;

}