package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.MultitenantEntity;
import lombok.Data;

import java.util.Date;

/**
 * 签约合同表
 * @Author: kongxj
 * @Date: 2020/7/30 17:44
 */
@Data
@TableName(value = "base_contract")
public class BaseContract extends MultitenantEntity {

    private static final long serialVersionUID = -2402351780723966417L;

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
    private Date signTime;

    /**
     * 签约状态
     */
    private String signStatus;

    /**
     * 签约失败原因
     */
    private String failureReason;



}
