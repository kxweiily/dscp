package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.entity.base.BaseActivityInform;
import com.topideal.dscp.entity.base.BaseMessageCenter;
import com.topideal.dscp.mapper.base.BaseMessageCenterMapper;
import com.topideal.dscp.service.base.BaseMessageCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 消息中心  ---接口实现类
 *
 * zhouhandong
 */
@Slf4j
@Service
public class BaseMessageCenterServiceImpl extends ServiceImpl<BaseMessageCenterMapper, BaseMessageCenter> implements BaseMessageCenterService {

    @Resource
    private BaseMessageCenterMapper baseMessageCenterMapper;

    /**
     * 异步执行数据插入
     *
     * @param baseActivityInform
     * @param list
     */
    @Override
    @Async
    public void insertAll(BaseActivityInform baseActivityInform, List<String> list) {
        log.info("BaseMessageCenterServiceImpl-->insertAll,param---->{}", baseActivityInform,list);
        Date now = new Date();
        List<BaseMessageCenter> messageCenterList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            BaseMessageCenter baseMessageCenter = new BaseMessageCenter();
            baseMessageCenter.setMerchantId(list.get(i));
            baseMessageCenter.setActivivtyId(baseActivityInform.getId());
            baseMessageCenter.setReadStatus(true);
            baseMessageCenter.setDetails(baseActivityInform.getDetails());
            baseMessageCenter.setTitle(baseActivityInform.getTitle());
            baseMessageCenter.setSendTime(now);
            baseMessageCenter.setType(baseActivityInform.getType());
            baseMessageCenter.setCreator(baseActivityInform.getVerifier());
            baseMessageCenter.setEditor(baseActivityInform.getVerifier());
            baseMessageCenter.setCreateTime(now);
            baseMessageCenter.setEditTime(now);
            messageCenterList.add(baseMessageCenter);
        }
        saveBatch(messageCenterList);
    }

    /**
     * 消息中心  分页查询
     *
     * @param page 分页参数
     * @param type 通知类型
     * @return
     */
    @Override
    public PcBaseMessageCenterRespDtoSum pcLoadPage(Page<?> page, Integer type,String merchantId,String userId) {
        log.info("BaseMessageCenterServiceImpl-->pcLoadPage,param--->{}",page,type,merchantId);
        PcBaseMessageCenterRespDtoSum pcBaseMessageCenterRespDtoSum = baseMessageCenterMapper.selectSum(merchantId,userId);

        Page<PcBaseMessageCenterRespDto> activityInformRespDtoPage = baseMessageCenterMapper.pcLoadPage(page, type,merchantId,userId);
        pcBaseMessageCenterRespDtoSum.setDtoList(activityInformRespDtoPage.getRecords());
        pcBaseMessageCenterRespDtoSum.setTotal(activityInformRespDtoPage.getTotal());
        pcBaseMessageCenterRespDtoSum.setSize(activityInformRespDtoPage.getSize());
        pcBaseMessageCenterRespDtoSum.setCurrent(activityInformRespDtoPage.getCurrent());
        return pcBaseMessageCenterRespDtoSum;
    }

    /**
     * 批量已读  /  单个已读
     *
     * @param ids  多个用','隔开
     * @return
     */
    @Override
    public void pcUpdate(String ids) {
        log.info("BaseMessageCenterServiceImpl-->pcUpdate,param--->{}",ids);
        List<String> idsList = Arrays.asList(ids.split(","));
        baseMessageCenterMapper.pcUpdate(idsList);
    }

    /**
     * 批量删除  /  单个删除
     *
     * @param ids
     * @return
     */
    @Override
    public void pcDelete(String ids) {
        log.info("BaseActivityInformServiceImpl-->pcDelete,param--->{}",ids);
        List<String> list = Arrays.asList(ids.split(","));
        baseMessageCenterMapper.pcDelete(list);
    }

    /**
     * 消息详情
     *
     * @param id
     * @return
     */
    @Override
    public PcBaseMessageCenterRespDto detail(String id) {
        log.info("BaseActivityInformServiceImpl-->pcDelete,param--->{}",id);
        if (StringUtils.isNotBlank(id)) {
            baseMessageCenterMapper.updateReadStatus(id);
            BaseMessageCenter baseMessageCenter = baseMessageCenterMapper.selectById(id);
            if (baseMessageCenter != null) {
                PcBaseMessageCenterRespDto pcBaseMessageCenterRespDto = new PcBaseMessageCenterRespDto();
                BeanUtils.copyProperties(baseMessageCenter, pcBaseMessageCenterRespDto);
                return pcBaseMessageCenterRespDto;
            }
        }

        return null;
    }
}
