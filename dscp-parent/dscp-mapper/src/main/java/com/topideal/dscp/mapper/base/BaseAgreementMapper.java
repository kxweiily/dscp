package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.topideal.dscp.dto.response.base.BaseAgreementRespDto;
import com.topideal.dscp.dto.request.base.BaseAgreementReqDto;
import com.topideal.dscp.entity.base.BaseAgreement;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Mapper - 协议
 * @Author: kongxj
 * @Date: 2020/7/30 18:17
 */
@Repository
public interface BaseAgreementMapper extends BaseMapper<BaseAgreement> {

    /**
     * 分页查询协议
     * @param page     分页信息
     * @param baseAgreementReqDto  查询封装类
     * @return
     */
    Page<BaseAgreementRespDto> findPage(Page<?> page, @Param("query") BaseAgreementReqDto baseAgreementReqDto);





}
