package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.response.base.BaseAgreementRespDto;
import com.topideal.dscp.dto.request.base.BaseAgreementReqDto;
import com.topideal.dscp.dto.response.base.BaseAgreementTemplateRespDto;
import com.topideal.dscp.service.base.BaseAgreementService;
import com.topideal.dscp.service.base.BaseAgreementTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Controller 协议签署
 *
 * @Author: fcs
 * @Date: 2020/8/4 11:10
 */
@Slf4j
@RestController
@RequestMapping("/cms/agreement")
public class AgreementController extends BaseController {

    /**
     * 协议签署接口
     */
    @Resource
    private BaseAgreementService baseAgreementService;
    /**
     * 协议模板接口
     */
    @Resource
    private BaseAgreementTemplateService baseAgreementTemplateService;


    /**
     * 分页查询
     *
     * @param page           分页信息
     * @param agreementQuery 查询条件实体类
     * @return 结果
     */
    @GetMapping(value = "/loadPage")
    public Message loadPage(Page<?> page, BaseAgreementReqDto agreementQuery) {
        Page<BaseAgreementRespDto> baseAgreementDtos = baseAgreementService.findPage(page, agreementQuery);
        return Message.success(baseAgreementDtos);
    }

    /**
     * 查询模板
     *
     * @param tempId 模板id
     * @return 结果
     */
    @GetMapping(value = "/{tempId}")
    public Message selectByTempId(@PathVariable String tempId) {
        if (StringUtils.isBlank(tempId)) {
            throw new BizException("temp is null");
        }
        BaseAgreementTemplateRespDto baseAgreementTemplateRespDto = baseAgreementTemplateService.selectById(tempId);
        return Message.success(baseAgreementTemplateRespDto);
    }


}
