package com.topideal.dscp.dto.response.sys;

import com.topideal.dscp.dto.common.BaseDto;
import lombok.Data;

/**
 * DTO - 角色
 *
 * @Author: kongxj
 * @Date: 2020/6/8 13:54
 */
@Data
public class SysRoleRespDto extends BaseDto {

    private static final long serialVersionUID = -2943652692047916342L;

    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String discription;

    /**
     * 是否拥有此角色 0 没 1 有
     */
    private Boolean isPossess;

}