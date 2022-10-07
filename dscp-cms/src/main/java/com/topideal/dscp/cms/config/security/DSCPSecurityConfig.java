package com.topideal.dscp.cms.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security 核心配置类
 * @Author: kongxj
 * @Date: 2022/7/12 14:50
 */
@Configuration
@EnableWebSecurity
public class DSCPSecurityConfig {

    @Autowired
    private DSCPAccessDeniedHandler dscpAccessDeniedHandler;

    @Autowired
    private DSCPOncePerRequestFilter dscpOncePerRequestFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                .headers().frameOptions().disable()
                .and();

        http
                // 关闭Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http
                // 允许用户使用HTTP基于验证进行认证
                .httpBasic()
                .and();

        http
                // 在UsernamePasswordAuthenticationFilter前添加 JWT认证拦截器
                .addFilterBefore(dscpOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new ErrorPageFilterConfig(), FilterSecurityInterceptor.class)
                .authorizeRequests()

                // 无需认证权限访问 url
                .antMatchers("/cms/authentication/**", "/cms/common/**", "/test/**").permitAll()
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
//                .antMatchers("/doc.html").permitAll()

                // 在这里 JWT只是认证并获取登录信息
                // 访问权限通过 rbacService 来判断过滤
                // anyRequest().access() 认证失败需要用 accessDeniedHandler处理器来获取
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
//                .anyRequest().permitAll()
                .and();


        http
                // 异常处理
                .exceptionHandling()
                // 权限不足处理器 对应 AccessDeniedException 异常
                .accessDeniedHandler(dscpAccessDeniedHandler);

        // 允许跨域
        http.cors();


        return http.build();
    }
}
