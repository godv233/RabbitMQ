package com.example.demo.config.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**回退
 * @author 曾伟
 * @date 2020/2/4 13:10
 */
public class MyReturnCallBack  implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("消息被退回");
    }
}
