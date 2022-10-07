package com.topideal.dscp.cms.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.rest.common.BaseController;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.dto.request.base.BaseUserReqDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.response.sys.SysConfigMenuRespDto;
import com.topideal.dscp.interfaces.rabbitMQ.RabbitMQProducer;
import com.topideal.dscp.interfaces.redis.RedisStringUtils;
import com.topideal.dscp.interfaces.sms.XuanwuSMSUtils;
import com.topideal.dscp.service.base.BaseUserService;
import com.topideal.dscp.service.sys.SysConfigMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试
 * @Author: kongxj
 * @Date: 2022/7/5 17:59
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Resource
    private BaseUserService baseUserService;

    @Resource
    private SysConfigMenuService sysConfigMenuService;

    @Resource
    private RedisStringUtils redisStringUtils;

    @Resource
    private RabbitMQProducer rabbitMQProducer;

//    @Resource
//    private MongoDbUtils mongoDbUtils;

    @GetMapping("/mysql")
    public Message testMySql(){

        log.info("测试mysql连接");

//        BaseUser newUser = new BaseUser();
//        newUser.setName("123");
//        newUser.setPassword("123");
//        newUser.setTel("13790012221");
//        newUser.setEmail("3334234@qq.com");
//        String id = baseUserService.save(newUser);

        List<SysConfigMenuRespDto> lists = sysConfigMenuService.findConfigMenuByUserId("1545354703851614999");

        BaseUserRespDto user = baseUserService.findById("1545354703851614209");
        System.out.println(user.getName());

        Page<BaseUserRespDto> listMore = baseUserService.findPage(new Page<>(), new BaseUserReqDto());
        System.out.println(listMore.getRecords());


        return MessageForDMData(listMore);
    }

    @GetMapping("/redis")
    public Message testRedis(){

        log.info("测试redis连接");

        redisStringUtils.set("aa", "123", 60);

        log.info(redisStringUtils.get("aa").toString());

        return SUCCESS_MESSAGE;
    }

//    @GetMapping("/mq")
//    public Message testMq(){
//
//        log.info("测试mq连接");
//
//        rabbitMQProducer.sendSingleMessage(RabbitMQConstants.DSCP_EXCHANGE, RabbitMQConstants.TEST_BINDING, "hhhhhzz");
//
//        return SUCCESS_MESSAGE;
//    }

//    @GetMapping("/mongodb")
//    public Message testMongodb(){
//
//        log.info("测试mongodb连接");
//
//        mongoDbUtils.insertJson("test", "{'aa':123}");
//
//        return SUCCESS_MESSAGE;
//    }

    @GetMapping("/sms")
    public Message testSMS(){

        log.info("测试发短信");

        String template = "测试啦 编号：${code} 的人员 在 ${action}";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("code", "aA001");
        parameter.put("action", "打游戏");
        String a = XuanwuSMSUtils.send(template, parameter, "13790012221");

        System.out.println(a);

        return SUCCESS_MESSAGE;
    }


}
