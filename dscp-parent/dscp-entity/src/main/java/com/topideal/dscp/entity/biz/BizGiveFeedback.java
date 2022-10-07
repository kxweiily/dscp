package com.topideal.dscp.entity.biz;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;

import lombok.Data;

/**
 * 意见反馈
 */
@Data
@TableName(value = "biz_give_feedback" , autoResultMap = true)
public class BizGiveFeedback extends BaseEntity {
	/**
     * 类型 00-产品建议 01-功能异常 02-意见投诉 03-其他
     */
    private String type;
    /**
     * 关联订单
     */
    private String relevanceOrder;
    /**
     * 反馈内容
     */
    private String context;
    
    /**
     * 联系方式
     */
    private String tel;
    
    /**
     * 来源 00-APP端 01-PC端
     */
    private String source;
    
    /**
     * 图片1
     */
    private String img_uri1;
    
    /**
     * 图片2
     */
    private String img_uri2;
    
    /**
     * 图片3
     */
    private String img_uri3;
    
    /**
     * 状态 0-待处理 1-已处理 2-已关闭
     */
    private String status;
    
    /**
     * 处理时间
     */
    private Date handleTime;
    
    /**
     * 处理人
     */
    private String handler;
    
    /**
     * 备注
     */
    private String remark;
    /**
     * 商家id
     */
    private String enterpriseId;
}
