package com.example.demo.bean;

import lombok.Data;

/**
 * @author 曾伟
 * @date 2019/10/18 15:08
 */
@Data
public class Book {
    private  String name;
    private  String author;
    public Book(String name,String author){
        this.name=name;
        this.author=author;
    }
    public Book(){

    }
}
