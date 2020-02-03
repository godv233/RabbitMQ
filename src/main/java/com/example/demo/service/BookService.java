package com.example.demo.service;

import com.example.demo.bean.Book;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/10/18 15:15
 */
@Service
public class BookService {
    @RabbitListener(queues = "zengwei.news")
    public void receive(Book book){
        System.out.println("收到消息"+book);
    }
}
