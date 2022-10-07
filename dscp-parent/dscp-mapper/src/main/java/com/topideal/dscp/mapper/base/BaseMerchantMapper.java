package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.common.SelectBeanDto;
import com.topideal.dscp.dto.request.base.BaseMerchantReqDto;
import com.topideal.dscp.dto.response.base.BaseMerchantRespDto;
import com.topideal.dscp.entity.base.BaseMerchant;
import com.topideal.dscp.entity.base.BaseMessageCenter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 商家   ---->接口mapper
 *
 * @author fengchongshu
 */
@Repository
public interface BaseMerchantMapper extends BaseMapper<BaseMerchant> {

    /**
     * 商家列表查询
     *
     * @param page               分页
     * @param baseMerchant 查询参数
     * @return 结果集
     */
    Page<BaseMerchantRespDto> findPage(Page<?> page, @Param("query") BaseMerchant baseMerchant);

    /**
     * 查询所有商家的id
     * @return
     */
    @Select("select id from base_merchant")
    List<String> selectIdAll();


    /**
     * 商家下拉框
     * @param page
     * @return
     */
    Page<SelectBeanDto> selectsByManage(Page<?> page);

}
