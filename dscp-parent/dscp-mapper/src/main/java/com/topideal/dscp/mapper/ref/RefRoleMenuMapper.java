package com.topideal.dscp.mapper.ref;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topideal.dscp.entity.ref.RefRoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 角色权限关系
 *
 * @Author: kongxj
 * @Date: 2020年6月22日20:20:30
 */
public interface RefRoleMenuMapper extends BaseMapper<RefRoleMenu> {

    /**
     * 删除角色下所有权限关系
     * @param
     * @return
     */
    @Delete("delete from ref_role_menu where role_id = #{roleId}")
    void deleteByRoleId(@Param("roleId")String roleId);

    /**
     * 删除权限跟角色的关系
     * @param menuId
     */
    @Delete("delete from ref_role_menu where menu_id = #{menuId}")
    void deleteByMenuId(@Param("menuId") String menuId);
}