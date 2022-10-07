package com.topideal.dscp.entity.common;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基础类
 *
 * @Author: kongxj
 * @Date: 2020/6/8 13:54
 */
@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 312225214382407620L;

    /**
     * id主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)  //新增自动插入
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)  //新增,更新自动插入
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)  //新增自动插入
    private String creator;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)  //新增,更新自动插入
    private String editor;

    /**
     * 删除
     */
    @TableLogic  // 逻辑删除注解
    @JsonIgnore  // 忽略
    @TableField(fill = FieldFill.INSERT)  //新增自动插入
    private Boolean deleted;


}
