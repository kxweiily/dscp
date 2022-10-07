package com.topideal.dscp.cms.config.security;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.topideal.dscp.common.constants.RedisConstants;
import com.topideal.dscp.common.constants.TimeConstants;
import com.topideal.dscp.common.exception.AuthenticateException;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.jwt.JWTUtils;
import com.topideal.dscp.dto.request.base.LoginReqDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.response.base.LoginRespDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.entity.sys.SysUser;
import com.topideal.dscp.enums.sys.ConfigMenuType;
import com.topideal.dscp.interfaces.redis.RedisHashMapUtils;
import com.topideal.dscp.mapper.ref.RefUserRoleMapper;
import com.topideal.dscp.mapper.sys.SysUserMapper;
import com.topideal.dscp.service.base.BaseUserService;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 模拟 spring security 认证实现类
 *
 * @Author: kongxj
 * @Date: 2022/7/12 16:26
 */
@Slf4j
@Component("dscpAuthenticationManager")
public class DSCPAuthenticationManager {

    @Resource
    private BaseUserService baseUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RefUserRoleMapper refUserRoleMapper;


    @Resource
    private SysConfigMenuService sysConfigMenuService;

    @Resource
    private RedisHashMapUtils redisHashMapUtils;


    /**
     * 普通用户登录
     *
     * @param loginReqDto
     * @return
     * @throws AuthenticateException
     */
    public LoginRespDto authenticateBase(LoginReqDto loginReqDto) {
        String account = loginReqDto.getAccount(); // 输入的用户名
        String password = loginReqDto.getPassword(); // 输入的密码
        String verifyCode = loginReqDto.getVerifyCode(); // 验证码

        // 查询用户
        BaseUserRespDto baseUserRespDto = baseUserService.findByAccount(account);

        // 查无此用户
        if (baseUserRespDto == null || baseUserRespDto.getId() == null) {
            throw new AuthenticateException("用户登录验证失败, 账号不存在!");
        }

        if (StringUtils.isNotBlank(password)) { // 密码不为空 则验证密码
            if (!baseUserRespDto.getPassword().contentEquals(password)) {
                throw new AuthenticateException("用户登录验证失败, 密码不正确!");
            }

        } else if (!baseUserService.checkVerifyCode(account, verifyCode)) { // 验证验证码
            throw new AuthenticateException("用户登录验证失败, 验证码验证失败!");
        }


        // 组装 jwt
        JSONObject jwtJson = new JSONObject();
        jwtJson.put(JWTUtils.USER_ID, baseUserRespDto.getId());
        jwtJson.put(JWTUtils.ACCOUNT_TYPE, 1);
        if (StringUtils.isNotBlank(baseUserRespDto.getEnterpriseId())) {
            jwtJson.put(JWTUtils.ENTERPRISE_ID, baseUserRespDto.getEnterpriseId());
        }
        //加密数据  获得密文
        String token = JWTUtils.createJWT(jwtJson, TimeConstants.TOKEN_EXPIRE_TIME_FOR_CMS_MILLISE);


        // 用户token 存到redis中 限时3小时
        // 分组key：cms_jwt_token:用户id
        String key = RedisConstants.CMS_JWT_TOKEN + baseUserRespDto.getId();
        Map<String, Object> tokenMap = new HashMap<>();
        // jwt token
        tokenMap.put(RedisConstants.JWT_TOKEN_TOKEN_KEY, token);
        // 权限 普通用户无需权限
//        tokenMap.put(RedisConstants.JWT_TOKEN_PERMISSIONS_KEY, "");
        redisHashMapUtils.setMap(key, tokenMap, TimeConstants.TOKEN_EXPIRE_TIME_FOR_CMS);


        // 构建返回的用户登录成功的dto
        LoginRespDto respDto = new LoginRespDto();
        respDto.setToken(token);
        respDto.setUserId(baseUserRespDto.getId());
        respDto.setUserType(baseUserRespDto.getUserType().getCode());

        return respDto;
    }

    /**
     * 运营用户登录
     *
     * @param loginReqDto
     * @return
     * @throws AuthenticateException
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginRespDto authenticateSys(LoginReqDto loginReqDto) {
        // IDMcode
        String smsCode = loginReqDto.getIdmCode();
        if (StringUtils.isBlank(smsCode)){
            throw new BizException("缺少idm的code字段,登录失败");
        }

        SysUser sysUser = null;
        if (Objects.isNull(sysUser)) {
            throw new BizException("登录失败,请关闭页面重试");
        }

        String userId = sysUser.getId();
        // 查询用户所有权限 (包括菜单与按钮权限 用于前端页面显示控制与访问权限过滤)
        List<SysConfigMenuRespDto> sysConfigMenuLists = sysConfigMenuService.findConfigMenuByUserId(userId);
        // 路径列表 用于 RbacServiceImpl类 权限路径过滤
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<SysConfigMenuRespDto> newConfigMenuLists = sysConfigMenuLists;
        // 遍历嵌套的菜单权限
        while (CollectionUtils.isNotEmpty(newConfigMenuLists)) {
            // 子菜单集合
            List<SysConfigMenuRespDto> childSysConfigMenuLists = new ArrayList<>();
            for (SysConfigMenuRespDto dto : newConfigMenuLists) {
                // 获取 非空路由
                String route = dto.getRoute();
                if (StringUtils.isNotBlank(route)) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(route));
                }
                if (CollectionUtils.isNotEmpty(dto.getChildren())) {
                    for (SysConfigMenuRespDto children : dto.getChildren()) {
                        // 获取 “菜单”类型的子节点
                        if (ConfigMenuType.MENU.equals(children.getType())) {
                            childSysConfigMenuLists.add(children);
                        /* “数据”类型的子节点
                        / 特殊路由处理 “data:上级菜单路由”
                        / 因为 RbacServiceImpl 处理访问权限过滤的时候会知道当前请求的访问路径
                        / 在判断‘菜单’的访问权限时，同时也能判断是否拥有其数据权限
                        */
                        } else if (ConfigMenuType.DATA.equals(children.getType()) && StringUtils.isNotBlank(route)) {
                            String dataRoute = "data:" + route;
                            children.setRoute(dataRoute);
                            childSysConfigMenuLists.add(children);
                        }
                    }
                }
            }
            newConfigMenuLists = childSysConfigMenuLists;
        }
        // 组装 jwt
        JSONObject jwtJson = new JSONObject();
        jwtJson.put(JWTUtils.USER_ID, userId);
        jwtJson.put(JWTUtils.ACCOUNT_TYPE, 0);
        //加密数据  获得密文
        String token = JWTUtils.createJWT(jwtJson, TimeConstants.TOKEN_EXPIRE_TIME_FOR_CMS_MILLISE);
        // 用户token和菜单权限信息 存到redis中 限时3小时
        // 分组key：cms_jwt_token:用户id
        String key = RedisConstants.CMS_JWT_TOKEN + userId;
        Map<String, Object> tokenMap = new HashMap<>();
        // jwt token
        tokenMap.put(RedisConstants.JWT_TOKEN_TOKEN_KEY, token);
        // 当前用户拥有的菜单URL权限
        if (CollectionUtils.isNotEmpty(grantedAuthorities)) {
            tokenMap.put(RedisConstants.JWT_TOKEN_GRANTEDAUTHORITIES_KEY, JSONArray.toJSONString(grantedAuthorities));
        }
        redisHashMapUtils.setMap(key, tokenMap, TimeConstants.TOKEN_EXPIRE_TIME_FOR_CMS);
        // 构建返回的用户登录成功的dto
        LoginRespDto respDto = new LoginRespDto();
        respDto.setToken(token);
        respDto.setUserId(userId);
        respDto.setUserName(sysUser.getNickname());
        respDto.setSysConfigMenuRespDto(sysConfigMenuLists);
        return respDto;
    }

}
