package com.topideal.dscp.mapper.sys;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topideal.dscp.dto.request.sys.SysConfigMenuReqDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.entity.sys.SysConfigMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper - 菜单/按钮 权限
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:46
 */
public interface SysConfigMenuMapper extends BaseMapper<SysConfigMenu> {


    /**
     * 查询所有权限
     * @param sysConfigMenuReqDto
     * @return
     */
    List<SysConfigMenuRespDto> findAll(@Param("query") SysConfigMenuReqDto sysConfigMenuReqDto);

    /**
     * 通过用户id 嵌套查询其拥有的菜单权限
     * @param userId  用户id
     * @return List<SysConfigMenuRespDto>
     */
    List<SysConfigMenuRespDto> findConfigMenuByUserId(@Param("userId")String userId);

    /**
     * 根据角色id查询权限
     * @param roleId 角色id
     * @return List<所有权限，角色拥有的权限checked字段为true>
     */
    List<SysConfigMenuRespDto> findByRoleId(@Param("roleId")String roleId);

}