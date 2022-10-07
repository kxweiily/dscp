package com.topideal.dscp.cms;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * spring boot 启动类
 *
 * @Author: kongxj
 * @Date: 2020/6/5 18:20
 */
@Slf4j
@EnableScheduling //开启定时任务
// 禁止 mongodb 目前不用
@SpringBootApplication(scanBasePackages=("com.topideal.dscp"),
        exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan(basePackages = "com.topideal.dscp.mapper")
@EnableAsync
@EnableOpenApi
public class DscpCmsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DscpCmsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Ti DSCP CMS 启动完成");
    }

    /**
     * 解决不能注入session注册表问题
     */
    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
