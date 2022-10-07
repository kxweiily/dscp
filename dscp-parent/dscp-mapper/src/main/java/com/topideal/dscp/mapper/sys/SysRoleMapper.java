package com.topideal.dscp.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.sys.SysRoleReqDto;
import com.topideal.dscp.dto.response.sys.SysRoleRespDto;
import com.topideal.dscp.entity.sys.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper - 角色
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:46
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询全部角色
     * @param
     * @return
     */
    @Select("select * from sys_role where deleted = 0 order by create_time asc")
    List<SysRoleRespDto> findAll();

    /**
     * 通过用户id查询角色
     * @param userId
     * @return
     */
    @Select("select r.* from sys_role r join ref_user_role ur on ur.role_id = r.id and ur.deleted = 0 and ur.user_id = #{userId} where r.deleted = 0 order by r.create_time asc")
    List<SysRoleRespDto> findByUserId(@Param("userId")String userId);

    /**
     * 分页查询角色
     * @param page
     * @param roleReqDto
     * @return
     */

    Page<SysRoleRespDto> findPage(@Param("page") Page<?> page, @Param("query") SysRoleReqDto roleReqDto);

    /**
     * 根据用户id查询拥有的角色(包括所有角色)
     *
     * @param userId 用户
     * @return
     */
    List<SysRoleRespDto> findRoleByUserId(@Param("userId") String userId);
}