package com.topideal.dscp.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.sys.SysUserReqDto;
import com.topideal.dscp.dto.response.sys.SysUserRespDto;

import java.util.List;

/**
 * Service - 系统用户
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:54
 */
public interface SysUserService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysUserRespDto selectById(String id);

    /**
     * 根据用户名查询
     * @param name 用户名
     * @return
     */
    SysUserRespDto findByName(String name);

    /**
     * 分页查询系统用户
     * @param page     分页信息
     * @param sysUserReqDto  查询封装类
     * @return
     */
    Page<SysUserRespDto> findPage(Page<?> page, SysUserReqDto sysUserReqDto);

    /**
     * 通过角色id查询用户
     * @param roleId
     * @return
     */
    List<SysUserRespDto> findByRoleId(String roleId);

    /**
     * 保存
     * @param sysUserRespDto
     * @return
     */
    String save(SysUserRespDto sysUserRespDto);

    /**
     * 根据id更新
     * @param sysUserRespDto
     */
    void update(SysUserRespDto sysUserRespDto);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);

    /**
     * 是否存在相同用户账户
     * @param name 用户账户
     * @return
     */
    String existByName(String name);

    /**
     * 通过角色查询用户(包括所有用户)
     * @param roleId
     * @return
     */
    List<SysUserRespDto> refAllUser(String roleId);

    /**
     * 用户绑定角色
     * @param id
     * @param roleIds
     */
    void bindRole(String id, List<String> roleIds);
}
