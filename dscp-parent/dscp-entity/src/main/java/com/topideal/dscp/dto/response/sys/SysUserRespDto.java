package com.topideal.dscp.dto.response.sys;

import com.topideal.dscp.dto.common.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DTO - 用户
 *
 * @Author: kongxj
 * @Date: 2020/6/8 13:54
 */
@Data
@ApiModel("系统用户分页查询响应")
public class SysUserRespDto extends BaseDto {

    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 用户名
     */
    @ApiModelProperty(name = "name", value = "用户名")
    private String name;

    /**
     * 工号
     */
    @ApiModelProperty(name = "jobNumber", value = "工号")
    private String jobNumber;

    /**
     * 昵称
     */
    @ApiModelProperty(name = "nickname", value = "昵称")
    private String nickname;

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
     * 是否授权用户  0 否 1 是
     */
    private Boolean isAuthorization;

}