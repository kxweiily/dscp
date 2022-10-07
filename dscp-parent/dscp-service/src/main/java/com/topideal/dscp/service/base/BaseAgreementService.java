package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseAgreementRespDto;
import com.topideal.dscp.dto.request.base.BaseAgreementReqDto;


/**
 * Service - 协议
 * @Author: kongxj
 * @Date: 2020/7/31 10:10
 */
public interface BaseAgreementService {




    /**
     * 分页查询协议
     * @param page     分页信息
     * @param agreementQuery  查询封装类
     * @return
     */
    Page<BaseAgreementRespDto> findPage(Page<?> page, BaseAgreementReqDto agreementQuery);



}
