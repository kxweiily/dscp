package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.topideal.dscp.dto.common.SelectBeanDto;
import com.topideal.dscp.dto.request.base.BaseMerchantReqDto;
import com.topideal.dscp.dto.response.base.BaseMerchantRespDto;
import com.topideal.dscp.entity.base.BaseMerchant;

import java.util.List;

/**
 * 商家  --->  service接口
 * @author fengchongshu
 */
public interface BaseMerchantService  {
    /**
     * 商家查询
     * @param page 分页
     * @param  baseMerchantReqDto 查询参数
     * @return 结果集
     */
    Page<BaseMerchantRespDto> findPage(Page<?> page, BaseMerchantReqDto baseMerchantReqDto);

    /**
     * 商家列表查看详情
     * @param id id
     * @return 结果
     */
    BaseMerchantRespDto selectById(String id);

    /**
     * 模糊查询商家列表
     * @param name 查询参数
     * @return 商家列表
     */
    List<BaseMerchantRespDto> selectMerchants(String name);

    /**
     * 全部下拉框数据
     * @param page
     * @return
     */
    Page<SelectBeanDto> selectsByManage(Page<?> page);

    /**
     * 新增商家
     * @param baseMerchant
     */
    void insertMerchant(BaseMerchant baseMerchant);

    /**
     * 修改商家数据
     * @param baseMerchantReqDto
     * @return
     */
    int updateMerchant(BaseMerchantReqDto baseMerchantReqDto);
}
