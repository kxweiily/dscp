package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.dto.common.MultitenantDto;
import lombok.Data;

import java.util.Date;

/**
 * 签约合同DTO
 * @Author: kongxj
 * @Date: 2020/7/30 17:50
 */
@Data
public class BaseContractRespDto extends MultitenantDto {

    private static final long serialVersionUID = 2375939120546242286L;

    /**
     * 门店ID
     */
    private String storeId;

    /**
     * 协议ID
     */
    private String agreementId;

    /**
     * 签约人ID
     */
    private String contractorId;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 签约类型
     */
    private String signType;

    /**
     * 签约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signTime;

    /**
     * 签约状态
     */
    private String signStatus;

    /**
     * 签约失败原因
     */
    private String failureReason;


    /*******************  扩展字段 *********************/
    /**
     * 协议名称
     */
    private String agreementName;

    /**
     * 协议地址url
     */
    private String agreementUrl;

    /**
     * 签章定位关键字
     */
    private String sealKeyWord;

    /**
     * 签约人姓名
     */
    private String contractorName;

    /**
     * 签约人电话
     */
    private String contractorTel;

    /**
     * 签约人身份证
     */
    private String contractorIDCard;
}
