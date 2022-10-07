package com.topideal.dscp.dto.response.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.topideal.dscp.dto.common.BaseDto;
import com.topideal.dscp.enums.sys.ConfigMenuType;
import lombok.Data;

import java.util.List;

/**
 * DTO - 菜单/按钮 权限
 *
 * @Author: kongxj
 * @Date: 2020/6/8 13:54
 */
@Data
public class SysConfigMenuRespDto extends BaseDto {

    private static final long serialVersionUID = -8440783773492421796L;

    /**
     * 菜单标签名
     */
    @JsonProperty(value = "name")
    private String label;

    /**
     * 权限类型(菜单/按钮/数据)
     */
    private ConfigMenuType type;

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
    @JsonProperty(value = "pId")
    private String parentId;

    /**
     * 父级菜单名称
     */
    @JsonProperty(value = "pName")
    private String parentName;


    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 下级节点
     */
    private List<SysConfigMenuRespDto> children;


    /*----------     扩展字段      -----------*/
    /**
     * 角色是否拥有该权限
     */
    @JsonProperty(value = "checked")
    private Boolean checked;

    /**
     * 用户id
     */
    @JsonProperty(value = "userId")
    private String userId;

}