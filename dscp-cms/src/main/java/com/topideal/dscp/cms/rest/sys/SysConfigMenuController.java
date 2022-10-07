package com.topideal.dscp.cms.rest.sys;

import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.sys.SysConfigMenuReqDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.enums.sys.ConfigMenuType;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller 系统权限
 *
 * @Author: yucaixing
 * @Date: 2022/8/05
 */
@Slf4j
@RestController
@RequestMapping("/cms/configMenu")
public class SysConfigMenuController extends BaseController {

    @Resource
    private SysConfigMenuService sysConfigMenuService;


    /**
     * 根据用户id查询权限
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("/findConfigMenuByUserId/{id}")
    public Message findMenuByUserId(@PathVariable String id) {
        if (id == null) {
            return Message.error("id is empty");
        }
        List<SysConfigMenuRespDto> configMenuByUserId = sysConfigMenuService.findConfigMenuByUserId(id);
        return Message.success(configMenuByUserId);
    }

    /**
     * 查看所有的权限 树状结构
     *
     * @return
     */
    @GetMapping("/findConfigMenuAll")
    public Message findConfigMenuAll() {
        SysConfigMenuReqDto sysConfigMenuReqDto = new SysConfigMenuReqDto();
        List<SysConfigMenuRespDto> configMenuByUser = sysConfigMenuService.findAllTree(sysConfigMenuReqDto);
        return Message.success(configMenuByUser);
    }

    /**
     * 修改菜单上级菜单使用接口查询上级菜单
     *
     * @return
     */
    @GetMapping("/findAlPermission")
    public Message findAlPermission() {
        SysConfigMenuReqDto sysConfigMenuReqDto = new SysConfigMenuReqDto();
        sysConfigMenuReqDto.setType(ConfigMenuType.MENU.getCode());
        List<SysConfigMenuRespDto> configMenuByUser = sysConfigMenuService.findAllTree(sysConfigMenuReqDto);
        return Message.success(configMenuByUser);
    }


    /**
     * 更新权限
     *
     * @return
     */
    @PutMapping("/update/{id}")
    public Message update(@PathVariable String id, @RequestBody SysConfigMenuReqDto configMenuReqDto) {
        if (id == null) {
            return Message.error("id is empty");
        }
        configMenuReqDto.setId(id);
        sysConfigMenuService.update(configMenuReqDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 新增菜单
     *
     * @return
     */
    @PostMapping("/insertMenu")
    public Message insertMenu( @RequestBody SysConfigMenuReqDto configMenuReqDto) {
        sysConfigMenuService.insertMenu(configMenuReqDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 新增按钮
     *
     * @return
     */
    @PostMapping("/insertButton")
    public Message insertButton( @RequestBody SysConfigMenuReqDto configMenuReqDto) {
        sysConfigMenuService.insertButton(configMenuReqDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     *
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Message delete( @PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        sysConfigMenuService.delete(id);
        return SUCCESS_MESSAGE;
    }

    /**
     * 根据id查询详情
     *
     * @return
     */
    @GetMapping("/detail/{id}")
    public Message detail(@PathVariable String id) {
        SysConfigMenuRespDto configMenuRespDto = sysConfigMenuService.selectById(id);
        return Message.success(configMenuRespDto);
    }
}
