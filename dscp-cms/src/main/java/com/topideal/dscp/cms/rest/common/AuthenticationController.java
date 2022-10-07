package com.topideal.dscp.cms.rest.common;

import com.topideal.dscp.cms.config.security.DSCPAuthenticationManager;
import com.topideal.dscp.common.constants.RedisConstants;
import com.topideal.dscp.common.exception.AuthenticateException;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.LoginReqDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.response.base.LoginRespDto;
import com.topideal.dscp.interfaces.redis.RedisHashMapUtils;
import com.topideal.dscp.service.base.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录 / 注册 rest类
 * @Author: kongxj
 * @Date: 2022/7/13 11:10
 */
@Slf4j
@RestController
@RequestMapping("/cms/authentication")
public class AuthenticationController extends BaseController {

    @Resource
    private DSCPAuthenticationManager dscpAuthenticationManager;

    @Resource
    private BaseUserService baseUserService;

    @Resource
    private RedisHashMapUtils redisHashMapUtils;

    /**
     * 普通用户认证登录
     * @param loginReqDto
     * @return
     * @throws AuthenticateException
     */
    @PostMapping("/login/base")
    public Message loginBase(@RequestBody LoginReqDto loginReqDto) {
        // 登录方法
        LoginRespDto loginRespDto = dscpAuthenticationManager.authenticateBase(loginReqDto);

        return Message.success(loginRespDto);
    }

    /**
     * 运营用户认证登录
     * @param loginReqDto
     * @return
     * @throws AuthenticateException
     */
    @PostMapping("/login/sys")
    public Message loginSys(@RequestBody LoginReqDto loginReqDto) {
        // 登录方法
        LoginRespDto loginRespDto = dscpAuthenticationManager.authenticateSys(loginReqDto);

        return Message.success(loginRespDto);
    }

    /**
     * 普通用户注册
     * @param loginReqDto
     * @return
     * @throws AuthenticateException
     */
    @PostMapping(value = "/register")
    public Message register(@RequestBody LoginReqDto loginReqDto) {

        if (loginReqDto == null || StringUtils.isBlank(loginReqDto.getAccount())) {
            throw new BizException("用户注册失败, 账号信息为空！");
        }

        if (!StringUtils.equals(loginReqDto.getPassword(), loginReqDto.getRePassword())) {
            throw new BizException("用户注册失败, 请确认输入密码与确认密码一致！");
        }

        // 用户注册方法
        if (baseUserService.register(loginReqDto)) {

            // 成功后直接登录
            // 登录方法
            LoginRespDto loginRespDto = dscpAuthenticationManager.authenticateBase(loginReqDto);

            return Message.success(loginRespDto);
        }

        return Message.error("用户注册失败, 未知原因");
    }

    /**
     * 登出
     * @return
     */
    @GetMapping("/logout")
    public Message doLogout() {

        // 获取当前登录userId
        // 分组key：cms_jwt_token:用户id
        String key = RedisConstants.CMS_JWT_TOKEN + getCurrentUserId();
        // 删除redis key
        redisHashMapUtils.deleteKey(key);

        //清除上下文
        SecurityContextHolder.clearContext();

        return SUCCESS_MESSAGE;
    }

    /**
     * 判断 普通用户 账号是否存在
     * @param account
     * @return 存在则返回 UserDTO
     * @throws
     */
    @GetMapping(value = "/existAccount")
    public Message existAccount(String account) {

        if (StringUtils.isBlank(account)) {
            throw new BizException("账号不能为空!");
        }

        // 查询用户
        BaseUserRespDto baseUserRespDto = baseUserService.findByAccount(account);

        return MessageForDMData(baseUserRespDto);
    }


    /**
     * 普通用户 重置密码
     *
     * @param loginReqDto  更新实体对象
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/resetPassword")
    public Message resetPassword(@RequestBody LoginReqDto loginReqDto) {

        if (loginReqDto == null || StringUtils.isBlank(loginReqDto.getAccount())) {
            throw new BizException("用户密码重置失败, 账号信息为空！");
        }

        if (!StringUtils.equals(loginReqDto.getPassword(), loginReqDto.getRePassword())) {
            throw new BizException("用户密码重置失败, 请检查输入密码与确认密码！");
        }

        // 重置密码
        if (baseUserService.resetPassword(loginReqDto)) {

            // 成功后直接登录
            // 登录方法
            LoginRespDto loginRespDto = dscpAuthenticationManager.authenticateBase(loginReqDto);

            return Message.success(loginRespDto);
        }

        return Message.error("用户密码重置失败, 未知原因");
    }


}
