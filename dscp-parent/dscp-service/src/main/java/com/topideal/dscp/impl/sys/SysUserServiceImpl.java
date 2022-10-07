package com.topideal.dscp.impl.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.sys.SysUserReqDto;
import com.topideal.dscp.dto.response.sys.SysUserRespDto;
import com.topideal.dscp.entity.ref.RefUserRole;
import com.topideal.dscp.entity.sys.SysUser;
import com.topideal.dscp.mapper.ref.RefUserRoleMapper;
import com.topideal.dscp.mapper.sys.SysUserMapper;
import com.topideal.dscp.service.sys.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * ServiceImpl - 系统用户
 *
 * @Author: kongxj
 * @Date: 2020/6/12 14:02
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RefUserRoleMapper refUserRoleMapper;

    @Override
    @Transactional(readOnly = true)
    public SysUserRespDto selectById(String id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        SysUserRespDto sysUserRespDto = new SysUserRespDto();
        if (sysUser != null) {
            BeanUtils.copyProperties(sysUser, sysUserRespDto);
        }
        return sysUserRespDto;
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserRespDto findByName(String name) {
        return sysUserMapper.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SysUserRespDto> findPage(Page<?> page, SysUserReqDto sysUserReqDto) {
        return sysUserMapper.findPage(page, sysUserReqDto);
    }

    @Override
    public List<SysUserRespDto> findByRoleId(String roleId) {
        return sysUserMapper.findByRoleId(roleId);
    }

    @Override
    @Transactional
    public String save(SysUserRespDto sysUserRespDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRespDto, sysUser);

        sysUserMapper.insert(sysUser);
        return sysUser.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserRespDto sysUserRespDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRespDto, sysUser);

        sysUserMapper.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        sysUserMapper.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public String existByName(String name) {
        return sysUserMapper.existByName(name);
    }

    @Override
    public List<SysUserRespDto> refAllUser(String roleId) {
        return sysUserMapper.refAllUser(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRole(String id, List<String> roleIds) {
        // 删除原有用户跟角色关系
        refUserRoleMapper.deleteByUserId(id);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            // 插入新权限关系
            for (String roleId : roleIds) {
                RefUserRole refUserRole = new RefUserRole();
                refUserRole.setRoleId(roleId);
                refUserRole.setUserId(id);
                refUserRoleMapper.insert(refUserRole);
            }
        }
    }
}
