package com.topideal.dscp.cms.rest.biz;

import javax.annotation.Resource;

import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.interfaces.redis.RedisKeyEnum;
import com.topideal.dscp.interfaces.redis.RedisStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.biz.BizGiveFeedbackReqDto;
import com.topideal.dscp.dto.response.biz.BizGiveFeedbackRespDto;
import com.topideal.dscp.service.biz.BizGiveFeedbackService;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 意见反馈
 */
@Slf4j
@RestController
@RequestMapping("/cms/giveFeedback")
public class GiveFeedbackController extends BaseController {

    @Resource
    private BizGiveFeedbackService bizGiveFeedbackService;

    @Resource
    private RedisStringUtils redisStringUtils;

    /**
     * 分页查询
     *
     * @param page      分页信息
     * @param bizGiveFeedbackReqDto  查询条件实体类
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/loadPage")
    public Message loadPage(Page<?> page, BizGiveFeedbackReqDto bizGiveFeedbackReqDto){
    	Page<BizGiveFeedbackRespDto> pages = bizGiveFeedbackService.findPage(page, bizGiveFeedbackReqDto);
        return Message.success(pages);
    }

    /**
     * 回复
     *
     * @param id
     * @param bizGiveFeedbackRespDto  更新实体对象
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/{id}")
    public Message update(@PathVariable String id, @RequestBody BizGiveFeedbackRespDto bizGiveFeedbackRespDto) {
        if (id == null) return Message.error("id is empty");

        BizGiveFeedbackRespDto bizGiveFeedback = bizGiveFeedbackService.findById(id);
        if (bizGiveFeedback == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        bizGiveFeedback.setStatus("1");
		bizGiveFeedback.setRemark(bizGiveFeedbackRespDto.getRemark());
        bizGiveFeedbackService.update(bizGiveFeedback);
        return SUCCESS_MESSAGE;
    }


    /**
     * pc端   分页查询
     *
     * @param page
     * @param bizGiveFeedbackReqDto
     * @return
     */
    @GetMapping("/pc/loadPage")
    public Message pcLoadPage(Page<?> page ,BizGiveFeedbackReqDto bizGiveFeedbackReqDto) {
        String enterpriseId = getCurrentEnterpriseId();
        if (StringUtils.isBlank(enterpriseId)) {
            throw new BizException("当前登录用户没有绑定认证企业");
        }
        bizGiveFeedbackReqDto.setEnterpriseId(enterpriseId);
        Map<String,Object> map = bizGiveFeedbackService.pcLoadPage(page,bizGiveFeedbackReqDto);
        return Message.success(map);
    }

    /**
     * 新增反馈
     *
     * @param bizGiveFeedbackReqDto
     * @return
     */
    @PostMapping(value = "/save")
    public Message save(@RequestBody BizGiveFeedbackReqDto bizGiveFeedbackReqDto) {
        if (bizGiveFeedbackReqDto == null) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        String enterpriseId = getCurrentEnterpriseId();
        if (StringUtils.isBlank(enterpriseId)){
            throw new BizException("当前登录用户没有绑定认证企业");
        }

        //pc端数据新增
        bizGiveFeedbackReqDto.setSource("01");
        bizGiveFeedbackReqDto.setEnterpriseId(enterpriseId);
        bizGiveFeedbackService.insert(bizGiveFeedbackReqDto);
        return SUCCESS_MESSAGE;
    }

    /**
     *  详情
     *
     * @param id
     * @return
     */
    @GetMapping("/pc/detail/{id}")
    public Message detail(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        BizGiveFeedbackRespDto bizGiveFeedbackRespDto = bizGiveFeedbackService.selectById(id);
        return Message.success(bizGiveFeedbackRespDto);
    }

    /**
     * 检查订单号是否存在
     *
     * @param id
     * @return
     */
    @GetMapping("/check")
    public Message check(String id) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        bizGiveFeedbackService.check(id,getCurrentEnterpriseId());
        return SUCCESS_MESSAGE;
    }

    /**
     * 设置邮件发件人地址
     *
     * @return
     */
    @PostMapping("/saveEmail")
    public Message saveEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        //将邮件发件人地址保存到redis
        redisStringUtils.set(RedisKeyEnum.GIVEFEEDBACK_MAIL_ACCOUNT.getKey(), email);
        return SUCCESS_MESSAGE;
    }

    /**
     *  详情
     *
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    public Message details(@PathVariable String id) {
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        BizGiveFeedbackRespDto bizGiveFeedbackRespDto = bizGiveFeedbackService.selectById(id);
        return Message.success(bizGiveFeedbackRespDto);
    }

    /**
     * 关闭
     * @param id
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/colse/{id}")
    public Message colse(@PathVariable String id) {
        if (StringUtils.isBlank(id)) return new Message(Message.CodeEnum.ERROR_101);
        BizGiveFeedbackRespDto bizGiveFeedback = bizGiveFeedbackService.findById(id);
        if (bizGiveFeedback == null) {
            return new Message(Message.CodeEnum.ERROR_105);
        }
        bizGiveFeedback.setStatus("2");
        bizGiveFeedbackService.update(bizGiveFeedback);
        return SUCCESS_MESSAGE;
    }
}
