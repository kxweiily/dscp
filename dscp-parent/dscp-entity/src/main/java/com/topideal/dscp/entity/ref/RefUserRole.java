package com.topideal.dscp.entity.ref;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

/**
 * 用户-角色关系实体类
 *
 * @Author: lizhenni
 * @Date: 2020/6/12 11:30
 */
@Data
@TableName(value = "ref_user_role")
public class RefUserRole extends BaseEntity {
    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

}