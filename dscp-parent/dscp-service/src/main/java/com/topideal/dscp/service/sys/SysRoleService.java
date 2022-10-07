package com.topideal.dscp.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.sys.SysRoleReqDto;
import com.topideal.dscp.dto.response.sys.SysRoleRespDto;

import java.util.List;

/**
 * Service - 角色
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:52
 */
public interface SysRoleService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysRoleRespDto selectById(String id);

    /**
     * 查询全部角色
     * @param
     * @return
     */
    List<SysRoleRespDto> findAll();

    /**
     * 通过用户id查询角色
     * @param userId
     * @return
     */
    List<SysRoleRespDto> findByUserId(String userId);

    /**
     * 保存
     * @param roleReqDto
     * @return
     */
    String insert(SysRoleReqDto roleReqDto);

    /**
     * 根据id更新
     * @param roleReqDto
     */
    void update(SysRoleReqDto roleReqDto);

    /**
     * 角色绑定用户
     * @param id   角色id
     * @param userIds 用户ids
     */
    void bindUser(String id, List<String> userIds);

    /**
     * 角色绑定权限
     * @param id   角色id
     * @param permissionIds 权限ids
     */
    void bindPermission(String id, List<String> permissionIds);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 分页查询所有角色
     * @param page
     * @param roleReqDto
     * @return
     */
    Page<SysRoleRespDto> findPage(Page<?> page, SysRoleReqDto roleReqDto);

    /**
     * 根据用户id查询拥有的角色(包括所有角色)
     *
     * @param userId 用户
     * @return
     */
    List<SysRoleRespDto> findRoleByUserId(String userId);
}
