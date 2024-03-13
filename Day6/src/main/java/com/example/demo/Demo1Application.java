package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html");
        System.out.println("http://localhost:8080/h2-console");
    }

}
