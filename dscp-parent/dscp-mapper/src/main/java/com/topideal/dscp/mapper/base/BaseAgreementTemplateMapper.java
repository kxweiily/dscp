package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.base.BaseAgreementTemplateReqDto;
import com.topideal.dscp.dto.response.base.BaseAgreementTemplateRespDto;
import com.topideal.dscp.entity.base.BaseAgreementTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 协议模板
 * @author fengchongshu
 */
@Repository
public interface BaseAgreementTemplateMapper extends BaseMapper<BaseAgreementTemplate> {

    /**
     * 查询协议模板
     * @param page 分页
     * @param baseAgreementTemplateReqDto 查询类
     * @return 结果集
     */
    Page<BaseAgreementTemplateRespDto> findPage(Page<?> page, @Param("query") BaseAgreementTemplateReqDto baseAgreementTemplateReqDto);
}
