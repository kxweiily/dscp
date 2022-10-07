package com.topideal.dscp.cms.config.security;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson.JSONArray;
import com.topideal.dscp.common.constants.RedisConstants;
import com.topideal.dscp.common.jwt.JWTUtils;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.interfaces.redis.RedisHashMapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JWT 认证拦截器
 * @Author: kongxj
 * @Date: 2022/7/15 16:08
 */
@Slf4j
@Component
public class DSCPOncePerRequestFilter extends OncePerRequestFilter{

    @Resource
    private RedisHashMapUtils redisHashMapUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // header的值是在yml文件中定义的 “Authorization”
        String token = request.getHeader(JWTUtils.TOKEN_PREFIX);
        if (StringUtils.isNotBlank(token)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = JWTUtils.parseJWTJSON(token);
            } catch (Exception e) {
                log.error("Token： {} 解析出错，请求失败！", token);
                JSONAuthentication.WriteJSON(request, response, new Message(Message.CodeEnum.SYS_ERROR_411.getStatus(),"非法Token，请重新登陆"));
                return;
            }

            // 获取 token 中的 userId
            String userId = jsonObject.getString(JWTUtils.USER_ID);
            // 获取 token 中的 userType  0:系统用户   1:普通用户
            Integer accountType = jsonObject.getInteger(JWTUtils.ACCOUNT_TYPE);
            // 获取 token 中的 用户绑定商家id (普通用户专属)
            String enterpriseId = jsonObject.getString(JWTUtils.ENTERPRISE_ID);
            log.info("Token验证 --- userId:{}  accountType:{}", userId, accountType);

            // 分组key：cms_jwt_token:用户id
            String key = RedisConstants.CMS_JWT_TOKEN + userId;

            // 校验 redis 中的 token
            // 存在未过期 且与 header-token 与 redis-token 一致
            Map<String, Object> tokenMap = redisHashMapUtils.getMap(key);
            if (tokenMap.isEmpty()
                    || Objects.isNull(tokenMap.get(RedisConstants.JWT_TOKEN_TOKEN_KEY))
                    || !token.equals(tokenMap.get(RedisConstants.JWT_TOKEN_TOKEN_KEY))) {
                log.error("Token： {} 验证失败！", token);
                JSONAuthentication.WriteJSON(request, response, new Message(Message.CodeEnum.SYS_ERROR_411.getStatus(),"Token验证失败，请重新登陆"));
                return;
            }

            // 从redis中 获取用户权限
            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (!Objects.isNull(tokenMap.get(RedisConstants.JWT_TOKEN_GRANTEDAUTHORITIES_KEY))) {
                grantedAuthorities = JSONArray.parseArray(tokenMap.get(RedisConstants.JWT_TOKEN_GRANTEDAUTHORITIES_KEY).toString(), SimpleGrantedAuthority.class);
            }

            DscpUser dscpUser = new DscpUser(accountType, userId, enterpriseId, grantedAuthorities);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dscpUser, null, dscpUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        chain.doFilter(request, response);
    }
}
