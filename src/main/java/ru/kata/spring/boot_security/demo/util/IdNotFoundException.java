package ru.kata.spring.boot_security.demo.util;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(String msg){
        super(msg);
    }
}
