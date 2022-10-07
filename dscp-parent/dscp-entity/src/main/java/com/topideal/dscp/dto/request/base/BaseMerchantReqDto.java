package com.topideal.dscp.dto.request.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 商家---->请求
 * @author fengchongshu
 */
@Data
@ToString
public class BaseMerchantReqDto implements Serializable {
    private String id;
    /**
     * 企业编码
     */
    private String code;

    /**
     * 企业全称
     */
    private String name;

    /**
     * 证件类型
     * 枚举: Y,Z,G,H,J,S
     * 枚举备注: Y 营业执照 Z 注册登记证 G 个人身份证 H 护照 J 机构代码 S 社会团体法人登记证书
     */
    private String idType;


    /**
     * 注册时间
     */
    private String registeredDate;

    /**
     * 1：有效    0：锁定，默认有效
     */
    private Boolean status;
    /**
     * 商家类型
     */
    private String merchantType;


    /**
     * 是否白名称
     */
    private Boolean whiteNo;
}
