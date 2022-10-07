package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.common.util.DateUtils;
import com.topideal.dscp.common.util.RandomUtils;
import com.topideal.dscp.dto.request.base.BaseAgreementTemplateReqDto;
import com.topideal.dscp.dto.response.base.BaseAgreementTemplateRespDto;
import com.topideal.dscp.entity.base.BaseAgreementTemplate;
import com.topideal.dscp.enums.base.AgreementSignEnum;
import com.topideal.dscp.enums.base.AgreementStatusEnum;
import com.topideal.dscp.enums.base.AgreementTypeEnum;
import com.topideal.dscp.mapper.base.BaseAgreementTemplateMapper;
import com.topideal.dscp.service.base.BaseAgreementTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 协议模板  impl
 *
 * @author fengchongshu
 */
@Service
public class BaseAgreementTemplateServiceImpl implements BaseAgreementTemplateService {

    @Resource
    private BaseAgreementTemplateMapper baseAgreementTemplateMapper;

    /**
     * 查询
     *
     * @param page                        分页条件
     * @param baseAgreementTemplateReqDto 查询参数
     * @return
     */
    @Override
    public Page<BaseAgreementTemplateRespDto> findPage(Page<?> page, BaseAgreementTemplateReqDto baseAgreementTemplateReqDto) {
        return baseAgreementTemplateMapper.findPage(page, baseAgreementTemplateReqDto);
    }

    /**
     * 新增
     *
     * @param agreementTemplateReqDto 实体
     * @return 影响行数
     */
    @Override
    public int insert(BaseAgreementTemplateReqDto agreementTemplateReqDto) {
        BaseAgreementTemplate agreementTemplate = new BaseAgreementTemplate();
        BeanUtils.copyProperties(agreementTemplateReqDto, agreementTemplate);

        agreementTemplate.setSign(AgreementSignEnum.convertCode(agreementTemplateReqDto.getSign()));
        agreementTemplate.setType(AgreementTypeEnum.convertCode(agreementTemplateReqDto.getType()));
        agreementTemplate.setStatus(AgreementStatusEnum.ONE);
        //生成id
        agreementTemplate.setId(createId(agreementTemplate));
        return baseAgreementTemplateMapper.insert(agreementTemplate);
    }

    /**
     * 更新
     *
     * @param agreementTemplateReqDto 实体
     * @return 影响行数
     */
    @Override
    public int update(BaseAgreementTemplateReqDto agreementTemplateReqDto) {
        BaseAgreementTemplate agreementTemplate = new BaseAgreementTemplate();
        BeanUtils.copyProperties(agreementTemplateReqDto, agreementTemplate);

        agreementTemplate.setSign(AgreementSignEnum.convertCode(agreementTemplateReqDto.getSign()));
        agreementTemplate.setType(AgreementTypeEnum.convertCode(agreementTemplateReqDto.getType()));
        agreementTemplate.setStatus(AgreementStatusEnum.convertCode(agreementTemplateReqDto.getStatus()));
        return baseAgreementTemplateMapper.updateById(agreementTemplate);
    }

    /**
     * 查询模板详情
     *
     * @param id id
     * @return 查询实体
     */
    @Override
    public BaseAgreementTemplateRespDto selectById(String id) {
        BaseAgreementTemplate agreementTemplate = baseAgreementTemplateMapper.selectById(id);
        BaseAgreementTemplateRespDto baseAgreementTemplateRespDto = new BaseAgreementTemplateRespDto();
        BeanUtils.copyProperties(agreementTemplate, baseAgreementTemplateRespDto);
        return baseAgreementTemplateRespDto;
    }

    /**
     * 生成id
     *
     * @param agreementTemplate 新增实体
     * @return id
     */
    private String createId(BaseAgreementTemplate agreementTemplate) {
        StringBuffer id = new StringBuffer();
        String code = agreementTemplate.getSign().getCode();
        String date = DateUtils.format(new Date(), "yyyyMMddHHmmss");
        if (code.equals("1")) {
            id.append("W").append(date).append(RandomUtils.createRandom(4));
        } else if (code.equals("2")) {
            id.append("A").append(date).append(RandomUtils.createRandom(4));
        } else {
            id.append("P").append(date).append(RandomUtils.createRandom(4));
        }
        return id.toString();
    }





}

