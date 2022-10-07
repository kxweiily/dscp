package com.topideal.dscp.service.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.base.LoginReqDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.request.base.BaseUserReqDto;
import com.topideal.dscp.entity.base.BaseUser;

/**
 * Service - 用户
 *
 * @Author: kongxj
 * @Date: 2020/6/12 13:54
 */
public interface BaseUserService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    BaseUserRespDto findById(String id);

    /**
     * 分页查询系统用户
     * @param page     分页信息
     * @param baseUserReqDto  查询封装类
     * @return
     */
    Page<BaseUserRespDto> findPage(Page<?> page, BaseUserReqDto baseUserReqDto);

    /**
     * 通过用户账户(手机/邮箱)查询
     * @param account 用户账户(手机/邮箱)
     * @return
     */
    BaseUserRespDto findByAccount(String account);

    /**
     * 保存
     * @param baseUser
     * @return
     */
    String save(BaseUser baseUser);

    /**
     * 根据id更新
     * @param baseUser
     */
    Boolean update(BaseUser baseUser);

    /**
     * 根据id删除
     * @param id
     */
    Boolean delete(String id);

    /**
     * 发送账户验证码
     * @param account     账户
     */
    void sendVerifyCode(String account);

    /**
     * 验证 账户登录/注册/重置密码等 验证码
     * @param account     账户
     * @param verifyCode  验证码
     * @return
     */
    boolean checkVerifyCode(String account, String verifyCode);

    /**
     * 用户注册
     * @param loginReqDto     账户信息
     * @return
     */
    boolean register(LoginReqDto loginReqDto);

    /**
     * 账户重置密码
     * @param loginReqDto     账户信息
     */
    boolean resetPassword(LoginReqDto loginReqDto);

}
