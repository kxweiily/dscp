package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseContractRespDto;
import com.topideal.dscp.dto.request.base.BaseContractReqDto;
import com.topideal.dscp.entity.base.BaseContract;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper - 签约合同
 * @Author: kongxj
 * @Date: 2020/7/30 18:24
 */
public interface BaseContractMapper extends BaseMapper<BaseContract> {

    /**
     * 根据id查询重推签约合同相关信息
     * @param id
     * @return
     */
    BaseContractRespDto findForReSigningById(@Param("id")String id);

    /**
     * 分页查询协议
     * @param page     分页信息
     * @param baseContractReqDto  查询封装类
     * @return
     */
    Page<BaseContractRespDto> findPage(Page<?> page, @Param("query") BaseContractReqDto baseContractReqDto);


    /**
     * 根据签约人id 查询多条 签约合同
     * @param contractorId
     * @return
     */
    List<BaseContract> selectByContractorId(@Param("contractorId")String contractorId);

}
