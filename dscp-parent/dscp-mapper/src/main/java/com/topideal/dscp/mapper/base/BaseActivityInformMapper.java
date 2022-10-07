package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseActivityInformRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.entity.base.BaseActivityInform;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseActivityInformMapper extends BaseMapper<BaseActivityInform> {

    /**
     * 活动通知/平台公告 查询
     *
     * @param page
     * @param baseActivityInform
     * @return
     */
    Page<BaseActivityInformRespDto> findPage(Page<?> page, @Param("query") BaseActivityInform baseActivityInform,@Param("list")List<String> list);

    /**
     * 消息通知 - 系统通知查询
     *
     * @param page                     分页参数
     * @param baseActivityInform 查询参数
     * @return 结果
     */
    Page<BaseActivityInformRespDto> findSysPage(Page<?> page,@Param("query") BaseActivityInform baseActivityInform);

    /**
     * 消息中心  分页查询
     *
     * @param page 分页参数
     * @param type 通知类型
     * @return
     */
    Page<PcBaseMessageCenterRespDto> pcLoadPage(Page<?> page, @Param("type") Integer type);

    /**
     * 统计各个类型未读消息的总条数
     * @return
     */
    PcBaseMessageCenterRespDtoSum selectSum();

    /**
     * 批量已读
     *
     * @param idsList
     * @return
     */
    void pcUpdate(@Param("list") List<String> idsList);

    /**
     * 批量删除  /  单个删除
     *
     * @param list
     * @return
     */
    void pcDelete(@Param("list") List<String> list);

    /**
     * 修改已读状态
     *
     * @param id
     */
    @Update("update base_activity_inform set read_status = 0 where id = #{id}")
    void updateReadStatus(String id);
}
