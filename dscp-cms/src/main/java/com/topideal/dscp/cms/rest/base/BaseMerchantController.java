package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.BaseMerchantReqDto;
import com.topideal.dscp.dto.response.base.BaseMerchantRespDto;
import com.topideal.dscp.service.base.BaseMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商家控制器
 *
 * @author fengchongshu
 */
@RestController
@Slf4j
@RequestMapping("/cms/merchant")
public class BaseMerchantController extends BaseController {

    /**
     * 商家列表接口
     */
    @Resource
    private BaseMerchantService baseMerchantService;

    /**
     * 商家列表查询
     *
     * @param page               分页
     * @param baseMerchantReqDto 查询参数
     * @return 结果集
     */
    @GetMapping("/loadPage")
    public Message loadPage(Page<?> page, BaseMerchantReqDto baseMerchantReqDto) {
        log.info("BaseMerchantController----->loadPage,param--->{}", baseMerchantReqDto);
        Page<BaseMerchantRespDto> baseMerchantRespDtoPage = baseMerchantService.findPage(page, baseMerchantReqDto);
        return Message.success(baseMerchantRespDtoPage);
    }

    /**
     * 查询详情
     *
     * @param id id主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public Message selectById(@PathVariable("id") String id) {
        log.info("BaseMerchantController----->selectById,param--->{}", id);
        BaseMerchantRespDto baseMerchantRespDto = baseMerchantService.selectById(id);
        return Message.success(baseMerchantRespDto);
    }


    /**
     * 商家下拉框
     *
     * @param name
     * @return
     */
    @GetMapping("select/merchants")
    public Message selectMerchants(@RequestParam("name") String name) {
        log.info("BaseMerchantController----->selectMerchants,param--->{}", name);
        if (StringUtils.isBlank(name)) return Message.success(null);
        List<BaseMerchantRespDto> respDtos = baseMerchantService.selectMerchants(name);
        return Message.success(respDtos);
    }


    /**
     * 商家下拉框全部带分页
     *
     * @return
     */
    @GetMapping("/select/manage/merchants")
    public Message selectManagerMerchants(Page<?> page) {
        log.info("BaseMerchantController----->selectManagerMerchants");
        return Message.success(baseMerchantService.selectsByManage(page));
    }

    /**
     * 修改商家信息
     *
     * @param baseMerchantReqDto
     * @return
     */
    @PutMapping
    public Message updateMerchant(@RequestBody BaseMerchantReqDto baseMerchantReqDto) {
        log.info("BaseMerchantController----->updateMerchant");
        if (StringUtils.isBlank(baseMerchantReqDto.getId())) return new Message(Message.CodeEnum.ERROR_101);
        baseMerchantService.updateMerchant(baseMerchantReqDto);
        return SUCCESS_MESSAGE;
    }




}
