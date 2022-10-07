package com.topideal.dscp.cms.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @Author: kongxj
 * @Date: 2020/6/12 16:45
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //允许任何域名
                .allowedOriginPatterns("*")
                //允许任何方法
                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                //允许任何头
                .allowedHeaders("*")
                //暴露头
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                .maxAge(3600L); //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    }

}
