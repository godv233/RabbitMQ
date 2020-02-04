package com.example.demo.test;

import com.example.demo.bean.Book;
import com.example.demo.config.rabbit.MyConfirmCallBack;
import com.example.demo.config.rabbit.MyReturnCallBack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private static String EXCHANGE = "exchange.direct";
    private static String ROUTINGKEY = "zengwei.news";

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
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, book);
    }

    /**
     * 发送事务机制
     */
    @Test
    @Transactional(rollbackFor = Exception.class, transactionManager = "rabbitTransactionManager")
    public void test2() {
        for (int i = 0; i < 1000; i++) {
            Book book = new Book("java", "事务" + i);
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY, book);
        }
    }

    /**
     * 发送确认机制。和发送事务机制冲突，不能同时使用
     */
    @Test
    public void test3(){
        //返回的时候，异步回调该方法。来判断发送结果
        rabbitTemplate.setConfirmCallback(new MyConfirmCallBack());
        //true会监听接收到路由不可达的消息，false则broker直接删除.默认true
        rabbitTemplate.setMandatory(true);
        //路由不到，回调该方法返回message
        rabbitTemplate.setReturnCallback(new MyReturnCallBack());
        for (int i = 0; i < 1000; i++) {
            Book book = new Book("java", "confirm" + i);
            //回调时传输的一些数据
            CorrelationData correlationData=new CorrelationData();
            correlationData.setId(i+"");
            //rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY+"ad", book,correlationData);//错误路由
            Message message= MessageBuilder.withBody(book.toString().getBytes()).build();
            //持久化,默认是持久化的
           // message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            //不持久化
            //message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTINGKEY,message,correlationData);
        }
    }
}
