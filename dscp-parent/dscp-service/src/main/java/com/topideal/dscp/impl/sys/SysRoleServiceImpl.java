package com.topideal.dscp.impl.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.dto.request.sys.SysRoleReqDto;
import com.topideal.dscp.dto.response.sys.SysRoleRespDto;
import com.topideal.dscp.entity.ref.RefRoleMenu;
import com.topideal.dscp.entity.ref.RefUserRole;
import com.topideal.dscp.entity.sys.SysConfigMenu;
import com.topideal.dscp.entity.sys.SysRole;
import com.topideal.dscp.mapper.ref.RefRoleMenuMapper;
import com.topideal.dscp.mapper.ref.RefUserRoleMapper;
import com.topideal.dscp.mapper.sys.SysConfigMenuMapper;
import com.topideal.dscp.mapper.sys.SysRoleMapper;
import com.topideal.dscp.service.sys.SysRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ServiceImpl - 角色
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:59
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysConfigMenuMapper sysConfigMenuMapper;

    @Resource
    private RefUserRoleMapper refUserRoleMapper;

    @Resource
    private RefRoleMenuMapper refRoleMenuMapper;


    @Override
    @Transactional(readOnly = true)
    public SysRoleRespDto selectById(String id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        SysRoleRespDto sysRoleRespDto = new SysRoleRespDto();
        if (sysRole != null) {
            BeanUtils.copyProperties(sysRole, sysRoleRespDto);
        }
        return sysRoleRespDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRoleRespDto> findAll() {
        return sysRoleMapper.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysRoleRespDto> findByUserId(String userId) {
        return sysRoleMapper.findByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insert(SysRoleReqDto roleReqDto) {
        checkData(roleReqDto);
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleReqDto, sysRole);
        sysRoleMapper.insert(sysRole);
        return sysRole.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleReqDto roleReqDto) {
        checkData(roleReqDto);
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleReqDto, sysRole);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUser(String id, List<String> userIds) {
        // 删除原有用户关系
        refUserRoleMapper.deleteByRoleId(id);
        if (CollectionUtils.isNotEmpty(userIds)) {
            // 插入新用户关系
            for (String userId : userIds) {
                RefUserRole refUserRole = new RefUserRole();
                refUserRole.setRoleId(id);
                refUserRole.setUserId(userId);
                refUserRoleMapper.insert(refUserRole);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindPermission(String id, List<String> permissionIds) {
        // 删除原有权限关系
        refRoleMenuMapper.deleteByRoleId(id);
        //查询所有的权限
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            List<SysConfigMenu> sysConfigMenus = sysConfigMenuMapper.selectList(new QueryWrapper<>());
            Set<String> menuIds = new HashSet<>(permissionIds);
            if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
                Map<String, SysConfigMenu> configMenuMap = sysConfigMenus.stream().collect(Collectors.toMap(SysConfigMenu::getId, v -> v));
                for (String key : permissionIds) {
                    SysConfigMenu sysConfigMenu = configMenuMap.get(key);
                    //获取上级权限的菜单id,
                    getMenuParentId(menuIds, sysConfigMenu, configMenuMap);
                }
            }
            // 插入权限关系表
            for (String permissionId : menuIds) {
                RefRoleMenu refRoleMenu = new RefRoleMenu();
                refRoleMenu.setRoleId(id);
                refRoleMenu.setMenuId(permissionId);
                refRoleMenuMapper.insert(refRoleMenu);
            }
        }
    }

    private void getMenuParentId(Set<String> menuIds, SysConfigMenu sysConfigMenu, Map<String, SysConfigMenu> configMenuMap) {
        if (Objects.nonNull(sysConfigMenu)) {
            String parentId = sysConfigMenu.getParentId();
            if (StringUtils.isNotBlank(parentId) && !menuIds.contains(parentId)) {
                menuIds.add(parentId);
                if (StringUtils.isNotBlank(parentId)) {
                    SysConfigMenu parentConfigMenu = configMenuMap.get(parentId);
                    getMenuParentId(menuIds, parentConfigMenu, configMenuMap);
                }
            }

        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        QueryWrapper<RefUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", id);
        List<RefUserRole> refUserRoles = refUserRoleMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(refUserRoles)) {
            throw new BizException("！请解除该角色关联的用户");
        }
        //删除角色和角色和权限的关系
        sysRoleMapper.deleteById(id);
        refRoleMenuMapper.deleteByRoleId(id);
    }

    @Override
    public Page<SysRoleRespDto> findPage(Page<?> page, SysRoleReqDto roleReqDto) {
        return sysRoleMapper.findPage(page, roleReqDto);
    }

    @Override
    public List<SysRoleRespDto> findRoleByUserId(String userId) {
        return sysRoleMapper.findRoleByUserId(userId);
    }

    private void checkData(SysRoleReqDto roleReqDto) {
        int limitLength = 10;
        String name = roleReqDto.getName();
        if (StringUtils.isBlank(name)) {
            throw new BizException("角色名称不能为空");
        } else if (name.length() > limitLength) {
            throw new BizException("角色名称长度不能超过10个字符");
        }
        String discription = roleReqDto.getDiscription();
        limitLength = 50;
        if (StringUtils.isNotBlank(discription) && discription.length() > limitLength) {
            throw new BizException("角色描述不能超过50个字符");
        }
    }
}
