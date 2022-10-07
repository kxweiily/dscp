package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.dto.common.BaseDto;
import com.topideal.dscp.enums.base.AgreementSignEnum;
import com.topideal.dscp.enums.base.AgreementTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 协议DTO
 *
 * @Author: kongxj
 * @Date: 2020/7/30 17:43
 */
@Data
public class BaseAgreementRespDto extends BaseDto {

    private static final long serialVersionUID = 3921153087630806875L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 协议id
     */
    private String templateId;
    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signTime;


    //协议模板字段
    /**
     * 签约端
     */
    private AgreementSignEnum sign;
    /**
     * 协议名称
     */
    private String name;
    /**
     * 协议类型
     */
    private AgreementTypeEnum type;

}
