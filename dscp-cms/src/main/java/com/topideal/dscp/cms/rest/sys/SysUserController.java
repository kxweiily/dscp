package com.topideal.dscp.cms.rest.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.sys.SysUserReqDto;
import com.topideal.dscp.dto.response.sys.SysUserRespDto;
import com.topideal.dscp.service.sys.SysRoleService;
import com.topideal.dscp.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Controller 系统用户
 *
 * @Author: kongxj
 * @Date: 2020/6/17 13:36
 */
@Slf4j
@RestController
@Api(value = "系统用户", tags = {"系统用户rest"})
@RequestMapping("/cms/sysUser")
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 分页查询
     *
     * @param page          分页信息
     * @param sysUserReqDto 查询条件实体类
     * @return
     */
    @ApiOperation("系统用户-分页查询")
    @GetMapping(value = "/loadPage")
    public Message loadPage(Page<?> page, SysUserReqDto sysUserReqDto) {
        Page<SysUserRespDto> sysUserDto = sysUserService.findPage(page, sysUserReqDto);
        return Message.success(sysUserDto);
    }

    /**
     * 进入详情界面
     *
     * @return
     */
    @ApiOperation("系统用户-明细查询")
    @GetMapping(value = "/detail/{id}")
    public Message detail(@PathVariable String id, Model model) {
        SysUserRespDto sysUserRespDto = sysUserService.selectById(id);
        return Message.success(sysUserRespDto);
    }

    /**
     * 查询用户关联角色
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/refRole/{id}")
    public Message findRefRole(@PathVariable String id) {
        return Message.success(sysRoleService.findByUserId(id));
    }


    /**
     * 用户状态更新
     *
     * @param id
     * @param isEnabled 是否启用  0 禁用 1 启用
     * @return
     */
    @PutMapping(value = "/status/{id}")
    public Message updateStatus(@PathVariable String id, Boolean isEnabled) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }

        SysUserRespDto temSysUserRespDto = sysUserService.selectById(id);
        if (temSysUserRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_103);
        }

        SysUserRespDto newSysUserRespDto = new SysUserRespDto();
        newSysUserRespDto.setId(id);
//        newSysUserRespDto.setEditor(getCurrentUserName());
        newSysUserRespDto.setIsEnabled(isEnabled != null ? isEnabled : Boolean.TRUE);

        sysUserService.update(newSysUserRespDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    public Message delete(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        SysUserRespDto temSysUserRespDto = sysUserService.selectById(id);
        if (temSysUserRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_103);
        }

        sysUserService.delete(id);
        return SUCCESS_MESSAGE;
    }

    /**
     * 用户绑定角色
     *
     * @param id 用户id
     * @param roleIds 角色ids
     * @return
     */
    @PutMapping(value = "/bindRole/{id}")
    public Message bindRole(@PathVariable String id, @RequestBody List<String> roleIds) {
        if (id == null) {
            return Message.error("id is empty");
        }
        SysUserRespDto sysUserRespDto = sysUserService.selectById(id);
        if (ObjectUtils.isEmpty(sysUserRespDto)) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        sysUserService.bindRole(id, roleIds);
        return SUCCESS_MESSAGE;
    }
}
