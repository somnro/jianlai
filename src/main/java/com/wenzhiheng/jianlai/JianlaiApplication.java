package com.wenzhiheng.jianlai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ComponentScan(value = "com.wenzhiheng.jianlai.utils")
@EnableScheduling
public class JianlaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JianlaiApplication.class, args);
    }

}
