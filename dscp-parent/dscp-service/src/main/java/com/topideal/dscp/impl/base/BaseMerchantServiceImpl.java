package com.topideal.dscp.impl.base;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.dto.common.SelectBeanDto;
import com.topideal.dscp.dto.request.base.BaseMerchantReqDto;
import com.topideal.dscp.dto.response.base.BaseMerchantRespDto;
import com.topideal.dscp.entity.base.BaseMerchant;
import com.topideal.dscp.enums.base.MerchantTypeEnum;
import com.topideal.dscp.mapper.base.BaseMerchantMapper;
import com.topideal.dscp.service.base.BaseMerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fengchongshu
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BaseMerchantServiceImpl implements BaseMerchantService {

    /**
     * 商家mapper
     */
    @Resource
    private BaseMerchantMapper baseMerchantMapper;

    /**
     * 商家分页查询
     *
     * @param page               分页
     * @param baseMerchantReqDto 查询参数
     * @return
     */
    @Override
    public Page<BaseMerchantRespDto> findPage(Page<?> page, BaseMerchantReqDto baseMerchantReqDto) {
        log.info("BaseMerchantServiceImpl---->findPage,param---->{}", baseMerchantReqDto);
        BaseMerchant baseMerchant = new BaseMerchant();
        baseMerchant.setMerchantType(MerchantTypeEnum.convertCode(baseMerchantReqDto.getMerchantType()));
        BeanUtils.copyProperties(baseMerchantReqDto, baseMerchant);
        return baseMerchantMapper.findPage(page, baseMerchant);
    }

    /**
     * 商家列表查询详情
     *
     * @param id id
     * @return 结果
     */
    @Override
    public BaseMerchantRespDto selectById(String id) {
        log.info("BaseMerchantServiceImpl---->selectById,param---->{}", id);
        BaseMerchant baseMerchant = baseMerchantMapper.selectById(id);
        BaseMerchantRespDto baseMerchantRespDto = new BaseMerchantRespDto();
        BeanUtils.copyProperties(baseMerchant, baseMerchantRespDto);
        return baseMerchantRespDto;
    }


    /**
     * 模糊查询商家列表  limit10
     *
     * @param name 查询参数
     * @return 查询结果
     */
    @Override
    public List<BaseMerchantRespDto> selectMerchants(String name) {
        QueryWrapper<BaseMerchant> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name).orderByDesc("operate_time").last("limit 10");
        List<BaseMerchant> list = baseMerchantMapper.selectList(queryWrapper);
        List<BaseMerchantRespDto> listResp = CollectionUtil.newArrayList();
        if (CollectionUtil.isNotEmpty(list)) {
            listResp = list.stream().map(item -> {
                BaseMerchantRespDto respDto = new BaseMerchantRespDto();
                respDto.setId(item.getId());
                respDto.setName(item.getName());
                respDto.setShortName(item.getShortName());
                return respDto;
            }).collect(Collectors.toList());
        }
        return listResp;
    }

    /**
     * 商家下拉框
     *
     * @return
     */
    @Override
    public Page<SelectBeanDto> selectsByManage(Page<?> page) {
        return baseMerchantMapper.selectsByManage(page);
    }

    /**
     * 新增
     *
     * @param baseMerchant
     */
    @Override
    public void insertMerchant(@RequestBody BaseMerchant baseMerchant) {
        BaseMerchant merchant = baseMerchantMapper.selectOne(new QueryWrapper<BaseMerchant>().eq("code", baseMerchant.getCode()));
        if (ObjectUtil.isNotEmpty(merchant)) {
            throw new BizException("编号已存在！请核对商家信息！");
        }
        baseMerchantMapper.insert(baseMerchant);
    }

    /**
     * 修改
     *
     * @param baseMerchantReqDto
     * @return
     */
    @Override
    public int updateMerchant(@RequestBody BaseMerchantReqDto baseMerchantReqDto) {
        BaseMerchant baseMerchant = new BaseMerchant();
        BeanUtils.copyProperties(baseMerchantReqDto, baseMerchant);
        baseMerchant.setMerchantType(MerchantTypeEnum.convertCode(baseMerchantReqDto.getMerchantType()));
        return baseMerchantMapper.updateById(baseMerchant);
    }

}
