package com.topideal.dscp.impl.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.util.VerifyUtils;
import com.topideal.dscp.dto.request.sys.SysConfigMenuReqDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.entity.ref.RefRoleMenu;
import com.topideal.dscp.entity.sys.SysConfigMenu;
import com.topideal.dscp.enums.sys.ConfigMenuType;
import com.topideal.dscp.interfaces.config.ConfigSettings;
import com.topideal.dscp.mapper.ref.RefRoleMenuMapper;
import com.topideal.dscp.mapper.sys.SysConfigMenuMapper;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ServiceImpl - 菜单/按钮 权限
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:55
 */
@Service
public class SysConfigMenuServiceImpl implements SysConfigMenuService {

    @Resource
    private SysConfigMenuMapper sysConfigMenuMapper;

    @Resource
    private RefRoleMenuMapper refRoleMenuMapper;

    @Override
    public SysConfigMenuRespDto selectById(String id) {
        SysConfigMenu sysConfigMenu = sysConfigMenuMapper.selectById(id);
        SysConfigMenuRespDto sysConfigMenuRespDto = new SysConfigMenuRespDto();
        if (sysConfigMenu != null) {
            BeanUtils.copyProperties(sysConfigMenu, sysConfigMenuRespDto);
            String parentId = sysConfigMenu.getParentId();
            if (StringUtils.isNotBlank(parentId)) {
                SysConfigMenu parentConfigMenu = sysConfigMenuMapper.selectById(parentId);
                sysConfigMenuRespDto.setParentName(parentConfigMenu.getLabel());
            }
        }
        return sysConfigMenuRespDto;
    }


    @Override
    public String save(SysConfigMenuRespDto sysConfigMenuRespDto) {
        SysConfigMenu sysConfigMenu = new SysConfigMenu();
        BeanUtils.copyProperties(sysConfigMenuRespDto, sysConfigMenu);
        sysConfigMenuMapper.insert(sysConfigMenu);
        return sysConfigMenu.getId();
    }

    @Override
    public void update(SysConfigMenuReqDto configMenuReqDto) {
        String type = configMenuReqDto.getType();
        if (StringUtils.isBlank(type)) {
            throw new BizException("权限类型不能为空");
        }
        if (ConfigMenuType.DATA.getCode().equals(type)) {
            throw new BizException("数据权限不能修改");
        }
        SysConfigMenu configMenu = sysConfigMenuMapper.selectById(configMenuReqDto.getId());
        if (ObjectUtils.isEmpty(configMenu)) {
            throw new BizException("数据不存在");
        }
        //检查数据
        checkData(configMenuReqDto, ConfigMenuType.MENU.getCode().equals(configMenuReqDto.getType()), false);

        //下级有子菜单或者按钮的情况下不允许把没有url路径改为有或者有变没有
        String oldRoute = configMenu.getRoute();
        String newRoute = configMenuReqDto.getRoute();
        boolean change = StringUtils.isBlank(oldRoute) && StringUtils.isNotBlank(newRoute);
        boolean alter = StringUtils.isBlank(newRoute) && StringUtils.isNotBlank(oldRoute);
        if (change || alter) {
            QueryWrapper<SysConfigMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", configMenu.getId());
            wrapper.in("type", ConfigMenuType.MENU.getCode(), ConfigMenuType.BUTTON.getCode());
            List<SysConfigMenu> sysConfigMenus = sysConfigMenuMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
                throw new BizException("下级有子菜单或者按钮的情况下不允许把没有url路径改为有或者有变没有");
            }
        }
        SysConfigMenu sysConfigMenu = new SysConfigMenu();
        BeanUtils.copyProperties(configMenuReqDto, sysConfigMenu);
        sysConfigMenu.setType(ConfigMenuType.getType(configMenuReqDto.getType()));
        sysConfigMenuMapper.updateById(sysConfigMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        SysConfigMenu sysConfigMenu = sysConfigMenuMapper.selectById(id);
        if (ObjectUtils.isEmpty(sysConfigMenu)) {
            throw new BizException("要删除的数据不存在,请刷新页面");
        }
        //按钮没有下级 直接删除
        if (ConfigMenuType.BUTTON.equals(sysConfigMenu.getType())) {
            QueryWrapper<SysConfigMenu> wrapper = new QueryWrapper<>();
            wrapper.in("type", ConfigMenuType.BUTTON.getCode(), ConfigMenuType.MENU.getCode());
            wrapper.eq("parent_id", id);
            List<SysConfigMenu> sysConfigMenus = sysConfigMenuMapper.selectList(wrapper);
            if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
                throw new BizException("！请删除该菜单下的子菜单及按钮配置");
            }
            //菜单有路由的时候删除下面的数据权限
            if (StringUtils.isNotBlank(sysConfigMenu.getRoute())) {
                QueryWrapper<SysConfigMenu> deleteWrapper = new QueryWrapper<>();
                deleteWrapper.eq("parent_id", id);
                deleteWrapper.eq("type", ConfigMenuType.DATA.getCode());
                SysConfigMenu sysConfigMenuData = sysConfigMenuMapper.selectOne(deleteWrapper);
                if (ObjectUtils.isNotEmpty(sysConfigMenuData)) {
                    String dataId = sysConfigMenuData.getId();
                    sysConfigMenuMapper.deleteById(dataId);
                    refRoleMenuMapper.deleteByMenuId(dataId);
                }
            }
        }
        sysConfigMenuMapper.deleteById(id);
        refRoleMenuMapper.deleteByMenuId(id);
    }

    @Override
    public List<SysConfigMenuRespDto> findConfigMenuByUserId(String userId) {
        return sysConfigMenuMapper.findConfigMenuByUserId(userId);
    }

    @Override
    public List<SysConfigMenuRespDto> findByRoleId(String roleId) {
        List<SysConfigMenuRespDto> sysConfigMenus = sysConfigMenuMapper.findByRoleId(roleId);
        List<SysConfigMenuRespDto> configMenuRespDtos = ConvertTree(sysConfigMenus);
        return configMenuRespDtos;
    }

    @Override
    public List<SysConfigMenuRespDto> findAllTree(SysConfigMenuReqDto sysConfigMenuReqDto) {
        //查询所有的权限
        List<SysConfigMenuRespDto> sysConfigMenus = sysConfigMenuMapper.findAll(sysConfigMenuReqDto);
        List<SysConfigMenuRespDto> configMenuRespDtos = ConvertTree(sysConfigMenus);
        return configMenuRespDtos;
    }

    private List<SysConfigMenuRespDto> ConvertTree(List<SysConfigMenuRespDto> sysConfigMenus) {
        if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
            //获取非顶级的权限
            List<SysConfigMenuRespDto> subordinateList = sysConfigMenus.stream().filter(item -> StringUtils.isNotBlank(item.getParentId())).collect(Collectors.toList());
            Map<String, List<SysConfigMenuRespDto>> subordinateMap = null;
            if (CollectionUtil.isNotEmpty(subordinateList)) {
                subordinateMap = subordinateList.stream().collect(Collectors.groupingBy(SysConfigMenuRespDto::getParentId));
            }
            //获取顶级权限
            List<SysConfigMenuRespDto> parenList = sysConfigMenus.stream().filter(item -> StringUtils.isBlank(item.getParentId())).collect(Collectors.toList());
            List<SysConfigMenuRespDto> configMenuRespDtos = new ArrayList<>(parenList.size());
            //非顶级为空直接返回顶级权限
            if (CollectionUtil.isEmpty(subordinateMap)) {
                return parenList;
            }
            //遍历去获取下级数据
            for (SysConfigMenuRespDto configMenu : parenList) {
                configMenuRespDtos.add(findChildren(subordinateMap, configMenu));
            }
            return configMenuRespDtos;
        }
        return sysConfigMenus;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertMenu(SysConfigMenuReqDto configMenuReqDto) {
        //检查数据
        checkData(configMenuReqDto, true, true);
        //新增数据
        SysConfigMenu configMenu = new SysConfigMenu();
        BeanUtils.copyProperties(configMenuReqDto, configMenu);
        configMenu.setType(ConfigMenuType.MENU);
        sysConfigMenuMapper.insert(configMenu);
        //把菜单添绑定到管理员角色
        menuBindRole(configMenu.getId());
        //菜单有url路径默认添加数据权限
        if (StringUtils.isNotBlank(configMenuReqDto.getRoute())) {
            SysConfigMenu sysConfigMenu = new SysConfigMenu();
            sysConfigMenu.setLabel("数据权限");
            sysConfigMenu.setType(ConfigMenuType.DATA);
            sysConfigMenu.setParentId(configMenu.getId());
            sysConfigMenu.setSortOrder(99);
            sysConfigMenuMapper.insert(sysConfigMenu);
            menuBindRole(sysConfigMenu.getId());
        }
    }

    private void menuBindRole(String menuId) {
        RefRoleMenu refRoleMenu = new RefRoleMenu();
        // TODO
//        refRoleMenu.setRoleId(ConfigSettings.getAdminRoleId());
        refRoleMenu.setMenuId(menuId);
        refRoleMenuMapper.insert(refRoleMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertButton(SysConfigMenuReqDto configMenuReqDto) {
        checkData(configMenuReqDto, false, true);
        SysConfigMenu configMenu = new SysConfigMenu();
        BeanUtils.copyProperties(configMenuReqDto, configMenu);
        configMenu.setType(ConfigMenuType.BUTTON);
        sysConfigMenuMapper.insert(configMenu);
        //把按钮添绑定到管理员角色
        menuBindRole(configMenu.getId());
    }

    private void checkData(SysConfigMenuReqDto configMenuReqDto, boolean isMenu, boolean isInsert) {
        String label = configMenuReqDto.getLabel();
        if (StringUtils.isBlank(label)) {
            throw new BizException("菜单或者按钮名称不能为空");
        }
        int lengthBig = 10;
        if (label.length() > lengthBig) {
            throw new BizException("菜单或者按钮名称不能长度超过10个字符");
        }
        Integer sortOrder = configMenuReqDto.getSortOrder();
        int lengthSmall = 1;
        lengthBig = 99;
        if (ObjectUtils.isEmpty(sortOrder)) {
            throw new BizException("排序不能为空");
        } else if (sortOrder < lengthSmall || sortOrder > lengthBig) {
            throw new BizException("排序不能小于1或者大于99");
        }
        //查询排序是否存在
        String parentId = configMenuReqDto.getParentId();
        QueryWrapper<SysConfigMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("sort_order", configMenuReqDto.getSortOrder());
        SysConfigMenu configMenu = sysConfigMenuMapper.selectOne(wrapper);
        //新增跟更新判断不一样
        if (isInsert) {
            if (ObjectUtils.isNotEmpty(configMenu)) {
                throw new BizException("该序号已经被占用！");
            }
        } else {
            if (ObjectUtils.isNotEmpty(configMenu) && !configMenu.getId().equals(configMenuReqDto.getId())) {
                throw new BizException("该序号已经被占用！");
            }
        }
        //是否新增菜单
        if (isMenu) {
            //检查菜单数据
            checkMenuData(configMenuReqDto, isInsert);
        } else {
            lengthBig = 64;
            String permission = configMenuReqDto.getPermission();
            if (StringUtils.isBlank(permission) || permission.length() > lengthBig) {
                throw new BizException("标识符不能为空或者长度大于64字符");
            }else{
                if(VerifyUtils.isFull(permission) || VerifyUtils.isContainChinese(permission) ){
                    throw new BizException("标识符英文字符及特殊字符的格式为半角非中文字符格式,首字符不能为数字");
                }
                QueryWrapper<SysConfigMenu> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("permission",permission);
                queryWrapper.eq("type",ConfigMenuType.BUTTON.getCode());
                SysConfigMenu sysConfigMenu = sysConfigMenuMapper.selectOne(queryWrapper);
                if (Objects.nonNull(sysConfigMenu) && !sysConfigMenu.getId().equals(configMenuReqDto.getId()) ){
                    throw new BizException("标识符已存在，请重新输入");
                }
            }
            if (StringUtils.isBlank(configMenuReqDto.getParentId())) {
                throw new BizException("上级菜单不能为空");
            }
        }
    }

    private void checkMenuData(SysConfigMenuReqDto configMenuReqDto, boolean isInsert) {
        int lengthBig = 64;
        String permission = configMenuReqDto.getPermission();
        if (StringUtils.isNotBlank(permission) ) {
            if (permission.length() > lengthBig) {
                throw new BizException("CSS图标长度大于64字符");
            }
            if(VerifyUtils.isFull(permission) || VerifyUtils.isContainChinese(permission) ){
                throw new BizException("CSS图标英文字符及特殊字符的格式为半角非中文字符格式,首字符不能为数字");
            }
        }
        lengthBig = 255;
        String route = configMenuReqDto.getRoute();
        if (StringUtils.isNotBlank(route) && route.length() > lengthBig) {
            throw new BizException("URL路径长度大于255字符");
        }
        //修改菜单层级判断全部菜单层级不能超过三级
        int tier = 1;
        //查询上级菜单是否有URL路径或者层级是否超过三级
        String parentId = configMenuReqDto.getParentId();
        if (StringUtils.isNotBlank(parentId)) {
            tier = 2;
            QueryWrapper<SysConfigMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("id", parentId);
            SysConfigMenu configMenu = sysConfigMenuMapper.selectOne(wrapper);
            if (StringUtils.isNotBlank(configMenu.getRoute()) && StringUtils.isNotBlank(configMenuReqDto.getRoute())) {
                throw new BizException("在已有url路径的菜单下不能在添加子菜单");
            }
            if (StringUtils.isNotBlank(configMenu.getParentId())) {
                tier = 3;
                wrapper = new QueryWrapper<>();
                wrapper.eq("id", configMenu.getParentId());
                configMenu = sysConfigMenuMapper.selectOne(wrapper);
                if (StringUtils.isNotBlank(configMenu.getParentId())) {
                    throw new BizException("菜单层级已经超过三级");
                }
            }
            if (!isInsert) {
                //菜单层级限制 最多三层 查询当前更新
                int levelLimit = 4;
                wrapper = new QueryWrapper<>();
                wrapper.eq("parent_id", configMenuReqDto.getId());
                wrapper.eq("type", ConfigMenuType.MENU.getCode());
                List<SysConfigMenu> sysConfigMenus = sysConfigMenuMapper.selectList(wrapper);
                if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
                    tier += 1;
                    if (tier >= levelLimit) {
                        throw new BizException("菜单层级已经超过三级");
                    }
                    List<String> ids = sysConfigMenus.stream().map(SysConfigMenu::getId).collect(Collectors.toList());
                    wrapper = new QueryWrapper<>();
                    wrapper.in("parent_id", ids);
                    wrapper.eq("type", ConfigMenuType.MENU.getCode());
                    sysConfigMenus = sysConfigMenuMapper.selectList(wrapper);
                    if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
                        throw new BizException("菜单层级已经超过三级");
                    }
                }
            }
        }
    }

    private SysConfigMenuRespDto findChildren(Map<String, List<SysConfigMenuRespDto>> subordinateMap, SysConfigMenuRespDto sysConfigMenuRespDto) {
        //获取下级权限
        String id = sysConfigMenuRespDto.getId();
        List<SysConfigMenuRespDto> sysConfigMenus = subordinateMap.get(id);
        if (CollectionUtil.isNotEmpty(sysConfigMenus)) {
            for (SysConfigMenuRespDto sysConfigMenu : sysConfigMenus) {
                findChildren(subordinateMap, sysConfigMenu);
            }
            sysConfigMenuRespDto.setChildren(sysConfigMenus);
        }
        return sysConfigMenuRespDto;
    }


}
