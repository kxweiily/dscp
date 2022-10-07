package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.BaseAgreementTemplateReqDto;
import com.topideal.dscp.dto.response.base.BaseAgreementTemplateRespDto;
import com.topideal.dscp.service.base.BaseAgreementTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 协议模板
 *
 * @author fengchongshu
 */
@RestController
@Slf4j
@RequestMapping("/cms/agreement/template")
public class BaseAgreementTemplateController extends BaseController {
    @Resource
    private BaseAgreementTemplateService baseAgreementTemplateService;

    /**
     * 分页查询合同列表
     *
     * @param page                        page
     * @param baseAgreementTemplateReqDto 参数
     * @return 结果
     */
    @GetMapping(value = "/loadPage")
    public Message loadPage(Page<?> page, BaseAgreementTemplateReqDto baseAgreementTemplateReqDto) {
        log.info("BaseAgreementTemplateController-->loadPage,param---->{}", baseAgreementTemplateReqDto);
        Page<BaseAgreementTemplateRespDto> servicePage = baseAgreementTemplateService.findPage(page, baseAgreementTemplateReqDto);
        return Message.success(servicePage);
    }

    @PostMapping
    public Message insert(@RequestBody BaseAgreementTemplateReqDto baseAgreementTemplateReqDto) {
        log.info("BaseAgreementTemplateController-->insert,param---->{}", baseAgreementTemplateReqDto);
        baseAgreementTemplateService.insert(baseAgreementTemplateReqDto);
        return SUCCESS_MESSAGE;
    }

    @PutMapping
    public Message update(@RequestBody BaseAgreementTemplateReqDto baseAgreementTemplateReqDto) {
        log.info("BaseAgreementTemplateController-->update,param---->{}", baseAgreementTemplateReqDto);
        if (baseAgreementTemplateReqDto == null) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        baseAgreementTemplateService.update(baseAgreementTemplateReqDto);
        return SUCCESS_MESSAGE;
    }
}
