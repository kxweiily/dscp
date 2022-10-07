package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 协议表
 *
 * @Author: kongxj
 * @Date: 2020/7/30 17:35
 */
@Data
@TableName(value = "base_agreement")
public class BaseAgreement extends BaseEntity {

    private static final long serialVersionUID = 6700554403574959212L;

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
    /**
     * 签约失败原因
     */
    private String errorMsg;
}
