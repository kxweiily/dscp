package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.entity.base.BaseMessageCenter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息中心 ----接口mapper
 *
 * zhouhandong
 */
@Repository
public interface BaseMessageCenterMapper extends BaseMapper<BaseMessageCenter> {


    PcBaseMessageCenterRespDtoSum selectSum(@Param("merchantId") String merchantId,@Param("userId") String userId);

    Page<PcBaseMessageCenterRespDto> pcLoadPage(Page<?> page, @Param("type") Integer type,@Param("id") String merchantId,@Param("userId") String userId);

    void pcUpdate(List<String> idsList);

    void pcDelete(List<String> list);

    @Update("update base_message_center set read_status = 0 where id = #{id}")
    void updateReadStatus(String id);
}
