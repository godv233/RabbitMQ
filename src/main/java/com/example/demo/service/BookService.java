package com.example.demo.service;

import com.example.demo.bean.Book;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void receive(Book book,Message message, Channel channel) throws IOException {
        System.out.println("收到消息" + book);
        //手动应答.
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
    }
}
