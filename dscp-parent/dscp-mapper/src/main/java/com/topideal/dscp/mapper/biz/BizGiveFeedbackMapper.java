package com.topideal.dscp.mapper.biz;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.biz.BizGiveFeedbackReqDto;
import com.topideal.dscp.dto.response.biz.BizGiveFeedbackRespDto;
import com.topideal.dscp.entity.biz.BizGiveFeedback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Mapper - 意见反馈
 */
@Repository
public interface BizGiveFeedbackMapper extends BaseMapper<BizGiveFeedback> {

    /**
     * 分页查询
     * @param page     分页信息
     * @param bizGiveFeedbackReqDto  查询封装类
     * @return
     */
    Page<BizGiveFeedbackRespDto> findPage(Page<?> page, @Param("query") BizGiveFeedbackReqDto bizGiveFeedbackReqDto);

    Map<String, Object> selectByStatus(@Param("enterpriseId") String enterpriseId);

    //一般贸易进出口订单号
    ArrayList<String> selectImportExport(@Param("enterpriseId") String enterpriseId);
    //保税入区订单
    ArrayList<String> selectEntry(@Param("enterpriseId")String enterpriseId);
    //保税出区订单
    ArrayList<String> selectExit(@Param("enterpriseId")String enterpriseId);
    //调拨出入仓单
    ArrayList<String> selectAllot(@Param("enterpriseId")String enterpriseId);
}
