package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.topideal.dscp.dto.common.BaseDto;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 消息管理-活动通知  --->响应
 *
 * @author zhouhandong
 */
@Data
@ToString
public class BaseActivityInformRespDto extends BaseDto {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息类型 0：系统通知/1：活动通知/2：平台公告
     */
    private String type;

    /***
     * 状态 0：待审核/1：已审核
     */
    private Boolean status;

    /**
     * 消息详情
     */
    private String details;

    /**
     * 接收对象，商家名称
     */
    private String recipient;

    /**
     * 审核人
     */
    private String verifier;

    /**
     * 链接类型    0页面 1产品服务 2内容 3自定义
     */
    private Integer linkType;

    /**
     * 跳转页面
     */
    private String synthesize;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date verifyTime;

    /**
     * 是否已读 0已读/1未读
     */
    private Boolean readStatus;
}
