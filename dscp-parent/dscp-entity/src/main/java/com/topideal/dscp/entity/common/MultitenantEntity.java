package com.topideal.dscp.entity.common;

import lombok.Data;
import javax.persistence.MappedSuperclass;

/**
 * Entity - 多租户
 *
 * @Author: kongxj
 * @Date: 2020/6/8 14:07
 */

@Data
@MappedSuperclass
public abstract class MultitenantEntity extends BaseEntity {

    private static final long serialVersionUID = 4928039480811814985L;

    /**
     * 商家 id
     */
    private String enterpriseId;

}
