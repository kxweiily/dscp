package com.topideal.dscp.service.base;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.base.BaseActivityInformReqDto;
import com.topideal.dscp.dto.response.base.BaseActivityInformRespDto;
import com.topideal.dscp.entity.base.BaseActivityInform;

/**
 * 消息管理 - 活动通知/平台公告 ---> 接口
 *
 * @author zhouhandong
 */
public interface BaseActivityInformService {

    /**
     * 活动通知/平台公告 查询
     *
     * @param page                         分页参数
     * @param baseActivityInformReqDto     查询参数
     * @return
     */
    Page<BaseActivityInformRespDto> findPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto);

    /**
     * 活动通知/平台公告 新增
     *
     * @param baseActivityInformReqDto  新增参数
     */
    void insertInform(BaseActivityInformReqDto baseActivityInformReqDto);

    /**
     * 活动通知/平台公告  审核
     *
     * @param
     * @param
     */
    void updateStatus(BaseActivityInform baseActivityInform);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BaseActivityInformRespDto selectById(String id);

    /**
     *  更新
     *
     * @param baseActivityInformReqDto
     */
    void update(BaseActivityInformReqDto baseActivityInformReqDto);

    /**
     * 消息通知 - 系统通知查询
     * @param page                  分页参数
     * @param baseActivityInformReqDto   查询参数
     * @return 结果
     */
    Page<BaseActivityInformRespDto> findSysPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto);

}
