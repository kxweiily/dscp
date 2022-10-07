package com.topideal.dscp.dto.response.base;

import com.topideal.dscp.dto.common.BaseDto;
import com.topideal.dscp.enums.base.AgreementSignEnum;
import com.topideal.dscp.enums.base.AgreementStatusEnum;
import com.topideal.dscp.enums.base.AgreementTypeEnum;
import lombok.Data;
import lombok.ToString;

/**
 * @author fengchognshu
 */
@Data
@ToString
public class BaseAgreementTemplateRespDto extends BaseDto {


    /**
     * 名称
     */
    private String name;
    /**
     * 签约端
     */
    private AgreementSignEnum sign;
    /**
     * 协议类型
     */
    private AgreementTypeEnum type;
    /**
     * 状态
     */
    private AgreementStatusEnum status;
    /**
     * 协议模板
     */
    private String temp;


}
