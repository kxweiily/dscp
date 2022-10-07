package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.base.BaseAgreementTemplateReqDto;
import com.topideal.dscp.dto.response.base.BaseAgreementTemplateRespDto;

/**
 * 协议模板  service
 *
 * @author fengchongshu
 */
public interface BaseAgreementTemplateService {

    /**
     * 协议模板查询
     *
     * @param page                        分页条件
     * @param baseAgreementTemplateReqDto 查询参数
     * @return 查询数据
     */
    Page<BaseAgreementTemplateRespDto> findPage(Page<?> page, BaseAgreementTemplateReqDto baseAgreementTemplateReqDto);

    /**
     * 新增协议模板
     *
     * @param baseAgreementTemplateReqDto 实体
     * @return 影响行数
     */
    int insert(BaseAgreementTemplateReqDto baseAgreementTemplateReqDto);

    /**
     * 修改协议模板
     *
     * @param baseAgreementTemplateReqDto 实体
     * @return 影响行数
     */
    int update(BaseAgreementTemplateReqDto baseAgreementTemplateReqDto);

    /**
     * 根据模板id查询协议
     *
     * @param id id
     * @return 表结果
     */
    BaseAgreementTemplateRespDto selectById(String id);
}
