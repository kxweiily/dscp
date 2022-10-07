package com.topideal.dscp.dto.response.base;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * pc端  消息中心 -->响应
 *
 * zhouhandong
 */
@Data
@ToString
public class PcBaseMessageCenterRespDtoSum {

    /**
     * 总数
     */
    private Integer sum;

    /**
     * 系统通知数
     */
    private Integer systemSum;

    /**
     * 活动通知数
     */
    private Integer activitySum;

    /**
     * 平台公告数
     */
    private Integer afficheSum;

    /**
     * 返回数据集合
     */
    private List<PcBaseMessageCenterRespDto> dtoList;

    /**
     * 总条数
     */
    private long total;

    private long size;

    private long current;
}
