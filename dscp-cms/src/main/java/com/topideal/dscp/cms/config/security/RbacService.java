package com.topideal.dscp.cms.config.security;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * spring security 访问权限过滤
 * @Author: kongxj
 * @Date: 2020/6/16 15:45
 */
public interface RbacService {

    /**
     * 是否拥有权限
     * @param request
     * @param authentication
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}

@Component("rbacService")
class RbacServiceImpl implements RbacService {

    @Resource
    private SysConfigMenuService sysConfigMenuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 环境变量
    @Value("${spring.profiles.active}")
    private String profile;

    // 是否鉴权
    @NacosValue(value = "${authentication.authority}", autoRefreshed = true)
    private String authority;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;


        // 开发环境与测试环境 保留测试接口 不做访问权限过滤判断
        if (StringUtils.isNotBlank(profile) && StringUtils.isNotBlank(authority)) {
            if ("dev".equals(profile) || "test".equals(profile)) {
                if ("false".equals(authority)) {
                    return true;
                }
            }
        }

        if (principal instanceof DscpUser) { //首先判断先当前用户是否UserDetails对象。
            // 获取 账户类型
            Integer accountType = ((DscpUser) principal).getAccountType();
            // 获取 userId
            String userId = ((DscpUser) principal).getUserId();

            // 账户类型是系统用户
            // 按用户角色菜单URL权限过滤
            if (accountType != null && accountType == 0) {
                // 获取 当前用户拥有的菜单URL权限
                List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();
                // 注意这里不能用equal来判断，因为有些URL是有参数的，所以要用AntPathMatcher来比较
                for (GrantedAuthority g : grantedAuthorities) {
                    String url = g.getAuthority();
                    url = url + "/**"; // 路径匹配

                    // 判断菜单权限
                    String requestUrl = request.getRequestURI();
                    if (antPathMatcher.match(url, requestUrl)) {
                        hasPermission = true;
                        continue;
                    }
                    // 判断菜单下的数据权限
                    // data:上级菜单路由
                    String dataUrl = "data:" + requestUrl;
                    if (antPathMatcher.match(url, dataUrl)) {
                        ((DscpUser) principal).setHasDataAuthority(Boolean.TRUE);
                    }

                }
            }

            // 账户类型是普通用户
            if (accountType != null && accountType == 1) {
                // 获取 普通用户绑定商家id
                String enterpriseId = ((DscpUser) principal).getEnterpriseId();

                // TODO 按理没绑定商家 即为游客 只有小部分个人权限
                if (StringUtils.isBlank(enterpriseId)) {

                }

                hasPermission = true;
            }

        }
        return hasPermission;
    }

    /**
     * 获取系统运行环境变量
     * @return
     */
    private String getProfilesActive() {
        Map<String,String> map = System.getenv();
        return map.get("spring.profiles.active");
    }
}

/**
 * 权限不足处理器
 */
@Component
class DSCPAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 输出用户权限不足
        JSONAuthentication.WriteJSON(request, response, new Message(Message.CodeEnum.SYS_ERROR_401.getStatus(), Message.CodeEnum.SYS_ERROR_401.getContent()));
    }
}