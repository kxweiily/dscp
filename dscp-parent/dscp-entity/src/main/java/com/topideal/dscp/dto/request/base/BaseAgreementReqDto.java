package com.topideal.dscp.dto.request.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 协议 分页查询类
 *
 * @Author: kongxj
 * @Date: 2020/7/30 18:19
 */
@Data
public class BaseAgreementReqDto implements Serializable {


    /**
     * 用户id
     */
    private String userId;
    /**
     * 签约端
     */
    private String sign;
    /**
     * 签约时间start
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 签约时间end
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 协议名称
     */
    private String name;
    /**
     * 协议类型
     */
    private String type;
}
