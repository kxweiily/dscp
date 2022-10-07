package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.BaseActivityInformReqDto;
import com.topideal.dscp.dto.response.base.BaseActivityInformRespDto;
import com.topideal.dscp.entity.base.BaseActivityInform;
import com.topideal.dscp.service.base.BaseActivityInformService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 消息管理 - 活动通知/平台公告 ---> 控制层
 *
 * @author zhouhandong
 */
@Slf4j
@RestController
@RequestMapping("/cms/ActivityInform")
public class BaseActivityInformController extends BaseController {

    @Resource
    private BaseActivityInformService baseActivityInformService;

    /**
     * 消息通知 - 系统通知查询
     * @param page                  分页参数
     * @param baseActivityInformReqDto   查询参数
     * @return 结果
     */
    @GetMapping("/loadSysPage")
    public Message loadSysPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto){
        log.info("BaseActivityInformController-->loadSysPage,param---->{}", baseActivityInformReqDto);
        Page<BaseActivityInformRespDto> baseActivityInformRespDto = baseActivityInformService.findSysPage(page,baseActivityInformReqDto);
        return Message.success(baseActivityInformRespDto);
    }

    /**
     * 活动通知/平台公告 分页查询
     *
     * @param page                     分页参数
     * @param baseActivityInformReqDto 查询参数
     * @return
     */
    @GetMapping("/loadPage")
    public Message loadPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformController-->lodaPage,param--->{}", baseActivityInformReqDto);
        String str = "1,2";
        if (StringUtils.isBlank(baseActivityInformReqDto.getType())) {
            baseActivityInformReqDto.setType(str);
        }
        Page<BaseActivityInformRespDto> baseActivityInformRespDto = baseActivityInformService.findPage(page, baseActivityInformReqDto);
        return Message.success(baseActivityInformRespDto);
    }

    /**
     * 活动通知/平台公告 新增
     *
     * @param baseActivityInformReqDto 新增参数
     * @return
     */
    @PostMapping
    public Message insertInform(@RequestBody BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformController-->insertInform,param--->{}", baseActivityInformReqDto);
        if (baseActivityInformReqDto == null) {
            return new Message(Message.CodeEnum.ERROR_101);
        }

        baseActivityInformService.insertInform(baseActivityInformReqDto);
        return SUCCESS_MESSAGE;
    }

    /**
     * 活动通知/平台公告 审核
     *
     * @param id     id序号
     * @param status 是否审核   0：待审核/1：已审核
     * @return
     */
    @PutMapping(value = "/status/{id}")
    public Message updateStatus(@PathVariable String id, Boolean status) {
        log.info("BaseActivityInformController-->insertInform,param--->{}{}", id, status);
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        BaseActivityInformRespDto baseActivityInformRespDto = baseActivityInformService.selectById(id);
        if (baseActivityInformRespDto == null) {
            return new Message(Message.CodeEnum.ERROR_103);
        }

        baseActivityInformRespDto.setStatus(status);
        baseActivityInformRespDto.setVerifier(getCurrentUserId());
        baseActivityInformRespDto.setVerifyTime(new Date());

        BaseActivityInform baseActivityInform = new BaseActivityInform();
        BeanUtils.copyProperties(baseActivityInformRespDto,baseActivityInform);
        baseActivityInformService.updateStatus(baseActivityInform);
        return SUCCESS_MESSAGE;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Message selectById(@PathVariable String id) {
        log.info("BaseActivityInformController-->selectById,param--->{}", id);
        BaseActivityInformRespDto baseActivityInformRespDto = baseActivityInformService.selectById(id);
        return Message.success(baseActivityInformRespDto);
    }

    /**
     * 修改
     *
     * @param id
     * @param baseActivityInformReqDto 封装对象
     * @return
     */
    @PutMapping("/update/{id}")
    public Message update(@PathVariable String id, @RequestBody BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformController-->update,param--->{}", baseActivityInformReqDto);
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        baseActivityInformReqDto.setId(id);
        baseActivityInformService.update(baseActivityInformReqDto);
        return SUCCESS_MESSAGE;
    }
}
