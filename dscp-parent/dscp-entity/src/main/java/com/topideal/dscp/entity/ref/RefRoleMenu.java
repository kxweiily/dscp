package com.topideal.dscp.entity.ref;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

/**
 * 角色-菜单关系实体类
 *
 * @Author: lizhenni
 * @Date: 2020/6/12 11:30
 */
@Data
@TableName(value = "ref_role_menu")
public class RefRoleMenu extends BaseEntity {
    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 权限ID
     */
    private String menuId;

}