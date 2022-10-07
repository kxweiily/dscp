package com.topideal.dscp.entity.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * 消息中心 ---主类
 *
 * zhouhandong
 */
@Data
@TableName("base_message_center")
public class BaseMessageCenter {
    /**
     * 消息管理id
     */
    private String activivtyId;
    /**
     * 商家id
     */
    private String merchantId;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息类型 0：系统消息/1：活动通知/2：平台公告
     */
    private String type;
    /**
     * 消息内容
     */
    private String details;
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;
    /**
     * 已读状态 0已读/1未读
     */
    private Boolean readStatus;
    /**
     * id主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建时间
     */
    //@TableField(fill = FieldFill.INSERT)  //新增自动插入
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
   //@TableField(fill = FieldFill.INSERT_UPDATE)  //新增,更新自动插入
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String editor;

    /**
     * 删除
     */
    @TableLogic  // 逻辑删除注解
    @JsonIgnore  // 忽略
    private Boolean deleted;

}
