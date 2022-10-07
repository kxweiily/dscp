package com.topideal.dscp.dto.response.base;

import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录 dto
 * @Author: kongxj
 * @Date: 2022/7/13 16:02
 */
@Data
public class LoginRespDto implements Serializable {

    /**
     * 登录 token
     */
    private String token;

    /**
     * 登录 用户id
     */
    private String userId;

    /**
     * 登录 用户名
     */
    private String userName;

    /**
     * 用户类型
     * 普通用户 才返回 (用于前端判断菜单权限)
     */
    private String userType;

    /**
     * 菜单/按钮/数据 权限
     * 运营用户 才返回
     */
    private List<SysConfigMenuRespDto> sysConfigMenuRespDto;

}
