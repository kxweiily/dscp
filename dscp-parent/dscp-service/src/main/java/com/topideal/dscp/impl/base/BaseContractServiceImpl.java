package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseContractRespDto;
import com.topideal.dscp.dto.request.base.BaseContractReqDto;
import com.topideal.dscp.entity.base.BaseContract;
import com.topideal.dscp.mapper.base.BaseContractMapper;
import com.topideal.dscp.service.base.BaseContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ServiceImpl - 签约合同
 * @Author: kongxj
 * @Date: 2020/7/31 10:21
 */
@Service
public class BaseContractServiceImpl implements BaseContractService {

    @Resource
    private BaseContractMapper baseContractMapper;

    @Override
    @Transactional(readOnly = true)
    public BaseContractRespDto selectById(String id) {
        BaseContract baseContract = baseContractMapper.selectById(id);
        BaseContractRespDto baseContractRespDto = new BaseContractRespDto();
        if (baseContractRespDto != null) {
            BeanUtils.copyProperties(baseContract, baseContractRespDto);
        }
        return baseContractRespDto;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseContractRespDto findForReSigningById(String id) {
        return baseContractMapper.findForReSigningById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BaseContractRespDto> findPage(Page<?> page, BaseContractReqDto contractQuery) {
        return baseContractMapper.findPage(page, contractQuery);
    }

    @Override
    public String save(BaseContractRespDto baseContractRespDto) {
        BaseContract baseContract = new BaseContract();
        BeanUtils.copyProperties(baseContractRespDto, baseContract);
        baseContractMapper.insert(baseContract);
        return baseContract.getId();
    }

    @Override
    public void update(BaseContractRespDto baseContractRespDto) {
        BaseContract baseContract = new BaseContract();
        BeanUtils.copyProperties(baseContractRespDto, baseContract);
        baseContractMapper.updateById(baseContract);
    }

    @Override
    public void delete(String id) {
        baseContractMapper.deleteById(id);
    }

    @Override
    public List<BaseContractRespDto> selectByContractorId(String contractorId) {
        List<BaseContract> baseContract = baseContractMapper.selectByContractorId(contractorId);
        List<BaseContractRespDto> dtos = new ArrayList<>();
        if (baseContract!=null||baseContract.size()>0) {
            BaseContractRespDto dto =new BaseContractRespDto();
            for (BaseContract contract : baseContract) {
                BeanUtils.copyProperties(contract, dto);
                dtos.add(dto);
            }
        }
        return dtos;

    }
}
