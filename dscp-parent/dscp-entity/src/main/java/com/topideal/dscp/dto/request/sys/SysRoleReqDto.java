package com.topideal.dscp.dto.request.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO - 角色
 *
 * @Author: yucaixing
 * @Date: 2020/8/10
 */
@Data
public class SysRoleReqDto implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String discription;

}