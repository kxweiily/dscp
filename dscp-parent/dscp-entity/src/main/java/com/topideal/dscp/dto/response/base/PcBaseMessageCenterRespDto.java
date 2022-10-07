package com.topideal.dscp.dto.response.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * pc端  消息中心封装
 *
 * zhouhandong
 */
@Data
@ToString
public class PcBaseMessageCenterRespDto {

    /**
     * id
     */
    private String id;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息详情
     */
    private String details;

    /**
     * 是否已读 0已读/1未读
     */
    private Boolean readStatus;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;
}
