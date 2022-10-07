package com.topideal.dscp.dto.request.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 签约合同 分页查询类
 * @Author: kongxj
 * @Date: 2020/7/30 18:25
 */
@Data
public class BaseContractReqDto implements Serializable {

    /**
     * 商家id  用于数据隔离
     */
    private String kaId;

    /**
     * 协议名称/编号
     */
    private String agreement;

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
     * 签约时间开始
     */
    private String signTimeStart;

    /**
     * 签约时间结束
     */
    private String signTimeEnd;

    /**
     * 签约状态
     */
    private String signStatus;




}
