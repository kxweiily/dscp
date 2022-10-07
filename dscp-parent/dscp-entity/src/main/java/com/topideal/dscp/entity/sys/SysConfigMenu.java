package com.topideal.dscp.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import com.topideal.dscp.enums.sys.ConfigMenuType;
import lombok.Data;

/**
 * 菜单/按钮 权限
 *
 * @Author: lizhenni
 * @Date: 2020/6/12 11:30
 */
@Data
@TableName(value = "sys_config_menu")
public class SysConfigMenu extends BaseEntity {
    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 菜单标签名
     */
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
    private String parentId;

    /**
     * 排序
     */
    private Integer sortOrder;


}