package com.topideal.dscp.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO - 基础类
 *
 * @Author: kongxj
 * @Date: 2020/6/8 13:54
 */
@Data
public abstract class BaseDto implements Serializable {

    private static final long serialVersionUID = 312225214382407620L;

    /**
     *  主键id
     */
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
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
    private Boolean deleted;


}
