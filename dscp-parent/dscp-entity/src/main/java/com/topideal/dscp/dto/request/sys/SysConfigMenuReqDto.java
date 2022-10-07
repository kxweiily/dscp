package com.topideal.dscp.dto.request.sys;

import lombok.Data;

import java.io.Serializable;

/**
 * DTO - 菜单/按钮 权限
 *
 * @Author: yucaixing
 * @Date: 2022/8/8
 */
@Data
public class SysConfigMenuReqDto implements Serializable {

    /**
     * 主键id
     */
    private String id;
    /**
     * 菜单标签名
     */
    private String label;

    /**
     * 权限类型(菜单/按钮/数据)
     */
    private String type;

    /**
     * 路由
     */
    private String route;

    /**
     * 标识符
     */
    private String permission;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 排序
     */
    private Integer sortOrder;
}