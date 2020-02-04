package com.example.demo.service;

import com.example.demo.bean.Book;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author 曾伟
 * @date 2019/10/18 15:15
 */
@Service
public class BookService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "zengwei.news")
    public void receive1(Message message, Channel channel) throws IOException, InterruptedException {
        System.out.println("消费者1：收到消息");
        //消费者限流
        //channel.basicQos(0,1,false);
        //手动应答
        Thread.sleep(20);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

    }

//    @RabbitListener(queues = "zengwei.news")
//    public void receive2(Book book, Message message, Channel channel) throws IOException, InterruptedException {
//        System.out.println("消费者2：收到消息" + book);
//        //手动应答.
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        //Thread.sleep(20000);
//    }

}
