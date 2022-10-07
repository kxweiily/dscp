package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 消息管理-活动通知  --->主类
 *
 * @author zhouhandong
 */
@Data
@ToString
@TableName("base_activity_inform")
public class BaseActivityInform extends BaseEntity {

    private static final long serialVersionUID = -8716951440623172812L;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息类型 0：系统通知/1：活动通知/2：平台公告
     */
    private String type;
    
    /**
     * 消息详情
     */
    private String details;

    /**
     * 接收对象，商家名称
     */
    private String recipient;

    /**
     * 是否已读 0已读/1未读
     */
    private Boolean readStatus;

    /***
     * 状态 0：待审核/1：已审核
     */
    private Boolean status;

    /**
     * 审核人
     */
    private String verifier;

    /**
     * 审核时间
     */
    private Date verifyTime;

    /**
     * 链接类型    0页面 1产品服务 2内容 3自定义
     */
    private Integer linkType;

    /**
     * 跳转页面
     */
    private String synthesize;


}
