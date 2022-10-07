package com.topideal.dscp.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

/**
 * 系统角色实体类
 *
 * @Author: lizhenni
 * @Date: 2020/6/12 11:30
 */
@Data
@TableName(value = "sys_role")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String discription;

}