package com.topideal.dscp.service.sys;

import com.topideal.dscp.dto.request.sys.SysConfigMenuReqDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;

import java.util.List;

/**
 * Service - 菜单/按钮 权限
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:50
 */
public interface SysConfigMenuService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysConfigMenuRespDto selectById(String id);

    /**
     * 保存
     * @param sysConfigMenuRespDto
     * @return
     */
    String save(SysConfigMenuRespDto sysConfigMenuRespDto);

    /**
     * 根据id更新
     * @param configMenuReqDto
     */
    void update(SysConfigMenuReqDto configMenuReqDto);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 通过用户id 嵌套查询其拥有的菜单权限
     * @param userId  用户id
     * @return List<SysConfigMenuRespDto>
     */
    List<SysConfigMenuRespDto> findConfigMenuByUserId(String userId);

    /**
     * 根据角色id查询权限
     * @param roleId 角色id
     * @return List<所有权限，角色拥有的权限checked字段为true>
     */
    List<SysConfigMenuRespDto> findByRoleId(String roleId);

    /**
     * 查询所有的权限
     * @param sysConfigMenuReqDto
     * @return
     */
    List<SysConfigMenuRespDto> findAllTree(SysConfigMenuReqDto sysConfigMenuReqDto);

    /**
     * 新增菜单
     * @param configMenuReqDto
     */
    void insertMenu(SysConfigMenuReqDto configMenuReqDto);

    /**
     * 新增按钮
     * @param configMenuReqDto
     */
    void insertButton(SysConfigMenuReqDto configMenuReqDto);
}
