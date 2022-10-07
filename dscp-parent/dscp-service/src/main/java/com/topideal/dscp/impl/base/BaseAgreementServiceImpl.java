package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.topideal.dscp.dto.response.base.BaseAgreementRespDto;
import com.topideal.dscp.dto.request.base.BaseAgreementReqDto;
import com.topideal.dscp.mapper.base.BaseAgreementMapper;
import com.topideal.dscp.service.base.BaseAgreementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * ServiceImpl - 协议
 *
 * @Author: kongxj
 * @Date: 2020/7/31 10:12
 */
@Service
public class BaseAgreementServiceImpl implements BaseAgreementService {

    /**
     * 协议签署接口
     */
    @Resource
    private BaseAgreementMapper baseAgreementMapper;


    /**
     * 协议签署分页查询
     * @param page     分页信息
     * @param agreementQuery  查询封装类
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BaseAgreementRespDto> findPage(Page<?> page, BaseAgreementReqDto agreementQuery) {
        return baseAgreementMapper.findPage(page, agreementQuery);
    }



}
