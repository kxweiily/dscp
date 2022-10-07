package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.exception.AuthenticateException;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.BaseUserReqDto;
import com.topideal.dscp.dto.response.base.BaseMerchantRespDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.entity.base.BaseUser;
import com.topideal.dscp.service.base.BaseMerchantService;
import com.topideal.dscp.service.base.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Controller 普通用户
 *
 * @Author: kongxj
 * @Date: 2020/6/17 13:36
 */
@Slf4j
@RestController
@RequestMapping("/cms/baseUser")
public class BaseUserController extends BaseController {

    @Resource
    private BaseUserService baseUserService;

    @Resource
    private BaseMerchantService baseMerchantService;

    /**
     * 分页查询
     *
     * @param page           分页信息
     * @param baseUserReqDto 查询条件实体类
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/loadPage")
    public Message loadPage(Page<?> page, BaseUserReqDto baseUserReqDto) {
        Page<BaseUserRespDto> BaseUserRespDtoPage = baseUserService.findPage(page, baseUserReqDto);
        // 数据脱敏
        return MessageForDMData(BaseUserRespDtoPage);
    }

    /**
     * 获取用户的企业信息
     *
     * @return 企业信息
     */
    @GetMapping("/getMerchant")
    public Message getMerchantByUser() {
        if (StringUtils.isBlank(getCurrent().getEnterpriseId())) Message.success(null);
        BaseMerchantRespDto baseMerchantRespDto = baseMerchantService.selectById(getCurrent().getEnterpriseId());
        return Message.success(baseMerchantRespDto);
    }

    /**
     * 查询详情
     *
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public Message detail(@PathVariable String id) {
        BaseUserRespDto baseUserRespDto = baseUserService.findById(id);

        // 数据脱敏
        return MessageForDMData(baseUserRespDto);
    }

    /**
     * 保存
     *
     * @param baseUser 保存实体对象
     * @return
     */
    @ResponseBody
    @PostMapping()
    public Message save(@RequestBody BaseUser baseUser) {
        if (baseUser == null) {
            return Message.error("baseUserReqDto is empty");
        }

        // 校验用户账户唯一
//        if (StringUtils.isNotEmpty(baseUserService..existByName(sysUserRespDto.getName()))) return new Message(Message.CodeEnum.ERROR_106);

        baseUserService.save(baseUser);
        return SUCCESS_MESSAGE;
    }

    /**
     * 更新用户信息 (绑定手机/邮箱)
     *
     * @param id
     * @param baseUserReqDto 更新实体对象
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/{id}")
    public Message update(@PathVariable String id, @RequestBody BaseUserReqDto baseUserReqDto) {
        if (StringUtils.isBlank(id)) {
            throw new BizException("用户id不能为空!");
        }

        BaseUserRespDto baseUserRespDto = baseUserService.findById(id);
        if (baseUserRespDto == null) {
            throw new BizException("用户不存在!");
        }

        // 若要修改绑定的手机号或邮箱 则需要验证码验证
        if (StringUtils.isNotBlank(baseUserReqDto.getTel()) || StringUtils.isNotBlank(baseUserReqDto.getEmail())) {
            String verifyCode = baseUserReqDto.getVerifyCode();
            // 验证验证码
            if (!baseUserService.checkVerifyCode(baseUserReqDto.getTel(), verifyCode) && !baseUserService.checkVerifyCode(baseUserReqDto.getEmail(), verifyCode)) {
                throw new AuthenticateException("用户更新失败, 验证码验证失败!");
            }
        }

        // 更新用户信息
        BaseUser baseUser = new BaseUser();
        baseUser.setId(baseUserRespDto.getId());
        if (StringUtils.isNotBlank(baseUserReqDto.getTel())) {
            baseUser.setTel(baseUserReqDto.getTel());
        }
        if (StringUtils.isNotBlank(baseUserReqDto.getEmail())) {
            baseUser.setEmail(baseUserReqDto.getEmail());
        }
        baseUser.setHpUrl(baseUserReqDto.getHpUrl());

        baseUserService.update(baseUser);
        return SUCCESS_MESSAGE;
    }

}
