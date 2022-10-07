package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.entity.base.BaseActivityInform;
import com.topideal.dscp.entity.base.BaseMessageCenter;

import java.util.List;

/**
 * 消息中心  接口
 *
 * zhouhandong
 */
public interface BaseMessageCenterService extends IService<BaseMessageCenter> {

    /**
     * 批量插入数据
     *
     * @param baseActivityInform
     * @param list
     */
    void insertAll(BaseActivityInform baseActivityInform, List<String> list);

    /**
     * 消息中心  分页查询
     *
     * @param page  分页参数
     * @param type  通知类型
     * @return
     */
    PcBaseMessageCenterRespDtoSum pcLoadPage(Page<?> page, Integer type,String merchantId,String userId);

    /**
     * 批量已读  /  单个已读
     *
     * @param ids
     * @return
     */
    void pcUpdate(String ids);

    /**
     * 批量删除  /  单个删除
     *
     * @param ids
     * @return
     */
    void pcDelete(String ids);

    /**
     * 消息详情
     *
     * @param id
     * @return
     */
    PcBaseMessageCenterRespDto detail(String id);
}
