package com.topideal.dscp.test;

import com.topideal.dscp.dto.response.sys.SysUserRespDto;
import com.topideal.dscp.cms.DscpCmsApplication;
import com.topideal.dscp.service.sys.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 测试类
 * @Author: kongxj
 * @Date: 2020/6/9 13:41
 */
@Slf4j
@SpringBootTest(classes={DscpCmsApplication.class}) // 指定启动类
@ExtendWith(SpringExtension.class)
public class UserTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void testMySql(){
        log.info("测试mysql连接");
        SysUserRespDto sysUser = sysUserService.selectById("1271386211284210001");
        System.out.println(sysUser.getName());

    }

}

