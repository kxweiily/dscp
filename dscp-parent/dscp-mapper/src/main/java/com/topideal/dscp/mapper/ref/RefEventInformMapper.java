package com.topideal.dscp.mapper.ref;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.topideal.dscp.entity.ref.RefEventInform;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
/**
 * 用户-事件-模版关系表
 *
 * @Author: yucaixing
 * @Date: 2022/8/1
 */
public interface RefEventInformMapper extends BaseMapper<RefEventInform> {

    /**
     * 删除事件下所有模版关系
     * @param eventId 事件id
     * @return
     */
    @Delete("delete from ref_event_inform where event_id=#{eventId}")
    void deleteByEventId(@Param("eventId")String eventId);

    /**
     * 删除事件跟模版的关联
     * @param eventId 事件id
     * @param informId 模版id
     */
    @Delete("delete from ref_event_inform where event_id=#{eventId} and inform_id = #{informId} ")
    void cancelCorrelation(@Param("eventId") String eventId, @Param("informId") String informId);
}