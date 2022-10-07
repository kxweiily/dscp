package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseContractRespDto;
import com.topideal.dscp.dto.request.base.BaseContractReqDto;

import java.util.List;

/**
 * Service - 签约合同
 * @Author: kongxj
 * @Date: 2020/7/31 10:18
 */
public interface BaseContractService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    BaseContractRespDto selectById(String id);

    /**
     * 根据id查询重推签约合同相关信息
     * @param id
     * @return
     */
    BaseContractRespDto findForReSigningById(String id);

    /**
     * 分页查询签约合同
     * @param page     分页信息
     * @param contractQuery  查询封装类
     * @return
     */
    Page<BaseContractRespDto> findPage(Page<?> page, BaseContractReqDto contractQuery);

    /**
     * 保存
     * @param baseContractRespDto
     * @return
     */
    String save(BaseContractRespDto baseContractRespDto);

    /**
     * 根据id更新
     * @param baseContractRespDto
     */
    void update(BaseContractRespDto baseContractRespDto);

    /**
     * 根据id删除
     * @param id
     */
    void delete(String id);


    /**
     * 根据签约人id 查询多条 签约合同
     * @param contractorId
     * @return
     */
    List<BaseContractRespDto> selectByContractorId(String contractorId);


}
