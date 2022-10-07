package com.topideal.dscp.dto.common;

import lombok.Data;

/**
 * DTO - 多租户
 *
 * @Author: kongxj
 * @Date: 2020/6/8 14:07
 */
@Data
public abstract class MultitenantDto extends BaseDto {

    private static final long serialVersionUID = 4928039480811814985L;

    /**
     * 商家 id
     */
    private String enterpriseId;

}
