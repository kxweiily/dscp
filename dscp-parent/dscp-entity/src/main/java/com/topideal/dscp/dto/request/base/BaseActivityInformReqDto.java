package com.topideal.dscp.dto.request.base;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 消息管理 - 活动通知/平台公告 --->请求
 *
 * @author zhouhandong
 */
@Data
@ToString
public class BaseActivityInformReqDto implements Serializable {

    /**
     * 序号  主键
     */
    private String id;

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
     * 接收对象/通知对象，商家名称
     */
    private String recipient;

    /**
     * 消息详情 / 消息内容
     */
    private String details;

    /**
     * 链接类型    0页面 1产品服务 2内容 3自定义
     */
    private Integer linkType;

    /**
     * 跳转页面
     */
    private String synthesize;

}
