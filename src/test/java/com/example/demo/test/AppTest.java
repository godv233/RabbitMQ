package com.example.demo.test;

import com.example.demo.bean.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾伟
 * @date 2019/10/17 22:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AmqpAdmin amqpAdmin;
    //用于创建一些消息组件（交换器，队列，绑定规则等等）

    /**
     * 没有发送确认机制
     */
    @Test
    public void test1() {
        //需要自己传入参数。Message也需要自己构造
        //rabbitTemplate.send(exchange,routKey,message);

        //只需要传入要发送的对象。自动序列化。发送。 object就是消息体
        //rabbitTemplate.convertAndSend(exchange,routKey,object);
        Map<String, Object> map = new HashMap<>();
        map.put("曾伟", "第一个消息");
        map.put("godv", Arrays.asList("123", "232"));
        //发送一个map.对象使用的是jdk的方式序列化的。我们最好使用json的方式。
        Book book = new Book("java", "没有确认机制");
        //发送消息
        rabbitTemplate.convertAndSend("exchange.direct", "zengwei.news", book);
    }

}
