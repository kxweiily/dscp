package com.topideal.dscp.cms.mq;

import com.alibaba.fastjson.JSON;
import com.topideal.dscp.common.constants.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @Author: kongxj
 * @Date: 2022/7/6 17:00
 */
@Slf4j
@Component
public class TestMq {

//    @RabbitListener(bindings=@QueueBinding(exchange=@Exchange(value = RabbitMQConstants.DSCP_EXCHANGE, durable = "true"), key = RabbitMQConstants.TEST_BINDING, value=@Queue(value = RabbitMQConstants.TEST_QUEUE, durable = "true")))
//    public void pushShelfGoods(String message) {
//        log.info("MQ监听 接收到消息: " + message);
//
//    }
}