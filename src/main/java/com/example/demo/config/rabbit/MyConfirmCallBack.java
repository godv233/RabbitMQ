package com.example.demo.config.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author 曾伟
 * @date 2020/2/4 14:59
 */
public class MyConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        //可以在这里处理相关的逻辑
        if (b) {
            System.out.println("消息发送到exchange成功: "+correlationData);
        } else {
            System.out.println("消息发送到exchange失败："+s);
        }
    }
}
