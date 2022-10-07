package com.topideal.dscp.extApi;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring boot 启动类
 *
 * @Author: kongxj
 * @Date: 2020/6/5 18:20
 */
@Slf4j
@SpringBootApplication(scanBasePackages=("com.topideal.dscp"))
@EnableTransactionManagement
@MapperScan(basePackages = "com.topideal.dscp.mapper")
public class DscpExtApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DscpExtApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Ti DSCP 对外API端 启动完成");
    }

}
