package com.topideal.dscp.service.biz;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.biz.BizGiveFeedbackReqDto;
import com.topideal.dscp.dto.response.biz.BizGiveFeedbackRespDto;

/**
 * Service - 意见反馈
 */
public interface BizGiveFeedbackService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
	BizGiveFeedbackRespDto findById(String id);
	
	/**
     * 分页查询
     * @param page     分页信息
     * @param bizGiveFeedbackReqDto  查询封装类
     * @return
     */
    Page<BizGiveFeedbackRespDto> findPage(Page<?> page, BizGiveFeedbackReqDto bizGiveFeedbackReqDto);

    /**
     * 根据id更新
     * @param bizGiveFeedbackRespDto
     */
    void update(BizGiveFeedbackRespDto bizGiveFeedbackRespDto);


    /**
     * pc端   分页查询
     *
     * @param page
     * @param bizGiveFeedbackReqDto
     * @return
     */
    Map<String, Object> pcLoadPage(Page<?> page, BizGiveFeedbackReqDto bizGiveFeedbackReqDto);

    /**
     * 新增反馈
     *
     * @param bizGiveFeedbackReqDto
     * @return
     */
    void insert(BizGiveFeedbackReqDto bizGiveFeedbackReqDto);

    /**
     * 详情
     * @param id
     * @return
     */
    BizGiveFeedbackRespDto selectById(String id);

    /**
     * 检查订单号是否存在
     *
     * @param id
     * @return
     */
    void check(String id,String enterpriseId);

    /**
     * 修改状态为关闭
     *
     * @param status
     * @param id
     * @return
     */
    void status(String status, String id);
}
