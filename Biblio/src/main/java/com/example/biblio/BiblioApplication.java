package com.example.biblio;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BiblioApplication {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(BiblioApplication.class, args);
    }

}
