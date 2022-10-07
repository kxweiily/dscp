package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.dto.common.BaseDto;
import com.topideal.dscp.enums.base.MerchantTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 商家---->响应
 *
 * @author fengchongshu
 */
@Data
@ToString
public class BaseMerchantRespDto extends BaseDto {

    /**
     * 企业编码
     */
    private String code;

    /**
     * 企业全称
     */
    private String name;
    /**
     * 企业简称
     */
    private String shortName;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 1：有效    0：锁定，默认有效
     */
    private Boolean status;

    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registeredDate;

    /**
     * 商家类型
     */
    private MerchantTypeEnum merchantType;

    /**
     * 是否白名称
     */
    private Boolean whiteNo;

    /**
     * 授权委托书
     */
    private String poaUrl;

    /**
     * 信用代码
     */
    private String creditCode;
}
