package com.topideal.dscp.dto.request.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户分页查询类
 *
 * @Author: kongxj
 * @Date: 2020/6/17 14:01
 */
@Data
@ApiModel("系统用户分页查询请求")
public class SysUserReqDto implements Serializable {

    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

    /**
     * 工号
     */
    @ApiModelProperty(name = "jobNumber", value = "工号")
    private String jobNumber;

    /**
     * 手机号码
     */
    @ApiModelProperty(name = "tel", value = "手机号码")
    private String tel;

    /**
     * 邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱")
    private String email;

    /**
     * 是否启用  0 禁用 1 启用
     */
    @ApiModelProperty(name = "isEnabled", value = "是否启用  0 禁用 1 启用")
    private Boolean isEnabled;

    /**
     * 角色id
     */
    private String roleId;


}
