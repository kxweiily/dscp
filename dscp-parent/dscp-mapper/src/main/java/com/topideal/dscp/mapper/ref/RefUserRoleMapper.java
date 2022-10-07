package com.topideal.dscp.mapper.ref;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topideal.dscp.entity.ref.RefUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 用户角色关系
 *
 * @Author: kongxj
 * @Date: 2020年6月22日20:20:30
 */
public interface RefUserRoleMapper extends BaseMapper<RefUserRole> {

    /**
     * 删除角色下所有用户关系
     * @param roleId 角色id
     * @return
     */
    @Delete("delete from ref_user_role where role_id=#{roleId}")
    void deleteByRoleId(@Param("roleId")String roleId);

    /**
     * 删除用户下所有角色关系
     * @param userId 用户
     * @return
     */
    @Delete("delete from ref_user_role where user_id=#{userId}")
    void deleteByUserId(@Param("userId") String userId);
}