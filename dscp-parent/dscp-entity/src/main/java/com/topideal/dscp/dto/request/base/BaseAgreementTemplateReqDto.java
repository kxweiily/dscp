package com.topideal.dscp.dto.request.base;


import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


/**
 * 协议模板  请求
 *
 * @author fengchognhsu
 */
@Data
@ToString
public class BaseAgreementTemplateReqDto implements Serializable {
    /**
     * 协议id
     */
    private String id;
    /**
     * 签约端
     */
    private String sign;
    /**
     * 协议类型
     */
    private String type;
    /**
     * 协议名称
     */
    private String name;
    /**
     * 状态
     */
    private String status;

    /**
     * 协议模板地址
     */
    private String temp;

}
