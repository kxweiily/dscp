package com.topideal.dscp.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.sys.SysUserReqDto;
import com.topideal.dscp.dto.response.sys.SysUserRespDto;
import com.topideal.dscp.entity.sys.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper - 系统用户
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:46
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from sys_user where deleted = 0 and is_enabled = 1 and name = #{name} limit 1")
    SysUserRespDto findByName(@Param("name")String name);

    /**
     * 分页查询系统用户
     * @param page     分页信息
     * @param sysUserReqDto  查询封装类
     * @return
     */
    Page<SysUserRespDto> findPage(Page<?> page, @Param("query") SysUserReqDto sysUserReqDto);

    /**
     * 通过角色id查询用户
     * @param roleId
     * @return
     */
    List<SysUserRespDto> findByRoleId(@Param("roleId")String roleId);

    /**
     * 是否存在相同用户账户
     * @param name 用户账户
     * @return
     */
    @Select("select id from sys_user where deleted = 0 and name = #{name} limit 1")
    String existByName(@Param("name")String name);

    /**
     * 通过角色查询用户(包括所有用户)
     * @param roleId
     * @return
     */
    List<SysUserRespDto> refAllUser(@Param("roleId") String roleId);
}