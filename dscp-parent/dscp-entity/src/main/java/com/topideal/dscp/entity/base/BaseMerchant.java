package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topideal.dscp.entity.common.BaseEntity;
import com.topideal.dscp.enums.base.MerchantTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家---->主类
 *
 * @author fengchongshu
 */
@Data
@ToString
@TableName("base_merchant")
public class BaseMerchant extends BaseEntity {
    private static final long serialVersionUID = -87921924359272448L;

    /**
     * 企业编码（主数据编码）证件号+证件类型+租户进行清洗
     */
    private String code;
    /**
     * 企业全称(营业执照名称）
     */
    private String name;
    /**
     * 企业简称
     */
    private String shortName;
    /**
     * 企业性质 枚举: Q,G,S,Z
     * <p>
     * 枚举备注: Q:企业 G:个人 S:社会团体 Z:政府机构
     */
    private String merchantNature;
    /**
     * 客商类型   枚举: 01,10,11
     * <p>
     * 枚举备注: 01：客户；10：供应商；11供应商&客户
     */
    private String clientType;
    /**
     * 证件号码
     */
    private String idCard;
    /**
     * 是否白名称
     */
    private Boolean whiteNo;

    /**
     * 证件类型
     * 枚举: Y,Z,G,H,J,S
     * <p>
     * 枚举备注: Y 营业执照 Z 注册登记证 G 个人身份证 H 护照 J 机构代码 S 社会团体法人登记证书
     */
    private String idType;
    /**
     * 客户状态  枚举: 1,0
     * <p>
     * 枚举备注: 1：有效/0：锁定，默认有效
     */
    private Boolean status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 企业地址
     */
    private String address;
    /**
     * 注册地址
     */
    private String registeredAddress;
    /**
     * 注册时间
     */
    private String registeredDate;
    /**
     * 所属国  枚举备注: 传国家码表
     */
    private String country;
    /**
     * 下来源(主数据提供)
     */
    private String source;
    /**
     * 租户编码
     */
    private String tenantCode;
    /**
     * 操作时间yyyy-MM-dd HH:mm:ss
     */
    private String operateTime;
    /**
     * 操作人
     */
    private String operater;
    /**
     * 客户类型(企业角色)电商企业\仓储企业\物流公司\电商平台\支付企业\监管场所经营人\报关企业\委托单位\账册主体\资金方\客户\供应商;可多选考虑用置位方式：“000000000000”
     */
    private String type;
    /**
     * 版本号
     */
    private String version;
    /**
     * 是否内部企业，默认为否
     */
    private Boolean internalFlag;
    /**
     * 外文名称
     * 当所属国是142 中国时，不允许纯中文名称
     */
    private String foreignName;
    /**
     * 纳税人资格类型
     */
    private Integer taxpayersType;
    /**
     * 香港）商业登记证号
     * 当所属国country是香港110时，必填
     */
    private String businessRegistrationNo;
    /**
     * （香港）商业登记证届满日期
     * 当businessRegistrationNo 有值时，必填
     * yyyy-mm-dd
     */
    private String brnoExpiryDate;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 商家类型
     */
    private MerchantTypeEnum merchantType;

    /**
     * 授权委托书
     */
    private String poaUrl;

    /**
     * 企业信用代码
     */
    private String creditCode;

}
