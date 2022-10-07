package com.topideal.dscp.cms.rest.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.service.base.BaseMessageCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * pc端 消息中心 ---控制层
 *
 * zhouhandong
 */
@Slf4j
@RestController
@RequestMapping("/cms/messageCenter")
public class BaseMessageCenterController extends BaseController {

    @Resource
    private BaseMessageCenterService baseMessageCenterService;

    /**
     * 消息中心  分页查询
     *
     * @param page  分页参数
     * @param type  通知类型
     * @return
     */
    @GetMapping("/pc/loadPage")
    public Message pcLoadPage(Page<?> page, Integer type) {
        log.info("BaseMessageCenterController-->pcLoadPage,param--->{}", page,type);
        String userId = getCurrentUserId();
        String enterpriseId = getCurrentEnterpriseId();
        enterpriseId = "1570712125905588226";
        if (StringUtils.isBlank(enterpriseId)) {
            throw new BizException("当前登录用户没有绑定认证企业");
        }
        PcBaseMessageCenterRespDtoSum pcBaseMessageCenterRespDtoSum = baseMessageCenterService.pcLoadPage(page,type,enterpriseId,userId);
        return Message.success(pcBaseMessageCenterRespDtoSum);
    }

    /**
     * 批量已读  /  单个已读
     *
     * @param ids
     * @return
     */
    @PutMapping("/pc/read")
    public Message pcUpdate(String ids){
        log.info("BaseActivityInformController-->pcUpdate,param--->{}", ids);
        if (StringUtils.isBlank(ids)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        baseMessageCenterService.pcUpdate(ids);
        return SUCCESS_MESSAGE;
    }

    /**
     * 批量删除  /  单个删除
     *
     * @param ids
     * @return
     */
    @PutMapping("/pc/delete")
    public Message pcDelete(String ids) {
        log.info("BaseActivityInformController-->pcDelete,param--->{}", ids);
        if (StringUtils.isBlank(ids)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        baseMessageCenterService.pcDelete(ids);
        return SUCCESS_MESSAGE;
    }

    /**
     * 消息详情
     *
     * @param id
     * @return
     */
    @GetMapping("/pc/detail/{id}")
    public Message detail(@PathVariable String id) {
        log.info("BaseActivityInformController-->detail,param--->{}", id);
        if (StringUtils.isBlank(id)) {
            return new Message(Message.CodeEnum.ERROR_101);
        }
        PcBaseMessageCenterRespDto pcBaseMessageCenterRespDto = baseMessageCenterService.detail(id);
        return Message.success(pcBaseMessageCenterRespDto);
    }

}
