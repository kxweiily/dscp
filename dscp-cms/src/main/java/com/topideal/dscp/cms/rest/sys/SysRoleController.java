package com.topideal.dscp.cms.rest.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.sys.SysRoleReqDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.dto.response.sys.SysRoleRespDto;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import com.topideal.dscp.service.sys.SysRoleService;
import com.topideal.dscp.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller 系统角色
 *
 * @Author: kongxj
 * @Date: 2020/6/18 19:25
 */
@Slf4j
@RestController
@RequestMapping("/cms/role")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysConfigMenuService sysConfigMenuService;

    /**
     * 进入列表界面
     *
     * @return
     */
    @GetMapping()
    public String page(Model model) {
        model.addAttribute("roles", sysRoleService.findAll());
        return "settingManage/role/roleList";
    }

    /**
     * 查询所有的角色
     *
     * @return
     */
    @GetMapping("/findAll")
    public Message findAll() {
        List<SysRoleRespDto> sysRoleRespDtos = sysRoleService.findAll();
        return Message.success(sysRoleRespDtos);
    }


    /**
     * 分页查询所有角色
     *
     * @param page
     * @param roleRespDto
     * @return
     */
    @GetMapping("/findPage")
    public Message findPage(Page<?> page, SysRoleReqDto roleRespDto) {
        Page<SysRoleRespDto> sysRoleRespDtos = sysRoleService.findPage(page, roleRespDto);
        return Message.success(sysRoleRespDtos);
    }


    /**
     * 查询角色关联用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/refUser/{id}")
    public Message findRefUser(@PathVariable String id) {
        return Message.success(sysUserService.findByRoleId(id));
    }

    /**
     * 查询角色关联用户(包括所有的用户)
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/refAllUser/{id}")
    public Message refAllUser(@PathVariable String id) {
        if (id == null) {
            return Message.error("id is empty");
        }
        return Message.success(sysUserService.refAllUser(id));
    }


    /**
     * 查询角色关联权限(权限配置)
     *
     * @param id     角色id
     * @return
     */
    @GetMapping(value = "/refPermission/{id}")
    public Message findRefPermission(@PathVariable String id) {
        List<SysConfigMenuRespDto> permissions = sysConfigMenuService.findByRoleId(id);
        return Message.success(permissions);
    }

    /**
     * 保存角色
     *
     * @param roleRespDto 保存实体对象
     * @return
     */
    @PostMapping("/insert")
    public Message insert(@RequestBody SysRoleReqDto roleRespDto) {
        if (roleRespDto == null) {
            return Message.error("sysRoleRespDto is empty");
        }
        return Message.success(sysRoleService.insert(roleRespDto));
    }

    /**
     * 更新
     *
     * @param id
     * @param roleRespDto 更新实体对象
     * @return
     */
    @PutMapping(value = "/update/{id}")
    public Message update(@PathVariable String id, @RequestBody SysRoleReqDto roleRespDto) {
        if (id == null) {
            return Message.error("id is empty");
        }
        SysRoleRespDto temSysRoleRespDto = sysRoleService.selectById(id);
        if (temSysRoleRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }

        roleRespDto.setId(id);
        sysRoleService.update(roleRespDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 根据用户id查询拥有的角色(包括所有角色)
     *
     * @param id 用户
     * @return
     */
    @GetMapping(value = "/findRoleByUserId/{id}")
    public Message findRoleByUserId(@PathVariable String id) {
        if (id == null) {
            return Message.error("id is empty");
        }
        List<SysRoleRespDto> sysRoleRespDtos =  sysRoleService.findRoleByUserId(id);
        return Message.success(sysRoleRespDtos);
    }

    /**
     * 角色绑定用户
     *
     * @param id 角色id
     * @param userIds 用户ids
     * @return
     */
    @PutMapping(value = "/bindUser/{id}")
    public Message bindUser(@PathVariable String id, @RequestBody List<String> userIds) {
        if (id == null) {
            return Message.error("id is empty");
        }
        SysRoleRespDto temSysRoleRespDto = sysRoleService.selectById(id);
        if (temSysRoleRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        sysRoleService.bindUser(id, userIds);
        return SUCCESS_MESSAGE;
    }
    /**
     * 角色绑定权限
     *
     * @param id
     * @param permissionIds 权限id
     * @return
     */
    @PutMapping(value = "/bindPermission/{id}")
    public Message bindPermission(@PathVariable String id, @RequestBody List<String> permissionIds) {
        if (id == null) {
            return Message.error("id is empty");
        }
        SysRoleRespDto temSysRoleRespDto = sysRoleService.selectById(id);
        if (temSysRoleRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        sysRoleService.bindPermission(id, permissionIds);
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public Message delete(@PathVariable String id) {
        if (id == null) {
            return Message.error("id is empty");
        }
        SysRoleRespDto temSysRoleRespDto = sysRoleService.selectById(id);
        if (temSysRoleRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        sysRoleService.delete(id);
        return SUCCESS_MESSAGE;
    }

}
