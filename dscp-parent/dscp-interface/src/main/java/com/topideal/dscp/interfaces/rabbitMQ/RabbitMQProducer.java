package com.topideal.dscp.interfaces.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 消息发送公共类
 * @Author: kongxj
 * @Date: 2022/7/6 15:00
 */
@Slf4j
@Component
public class RabbitMQProducer {

    //定义RabbitMQ消息操作组件RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送mq信息 方法
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void sendSingleMessage(String exchange, String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
