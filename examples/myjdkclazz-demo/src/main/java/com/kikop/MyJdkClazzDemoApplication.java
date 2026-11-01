package com.kikop;


import com.kikop.action2.service.impl.AllPayService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MyJdkClazzDemoApplication implements CommandLineRunner {




    public void run(String... args) throws Exception {
        System.out.println("------MyJdkClazzDemoApplication start------");
    }

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(MyJdkClazzDemoApplication.class, args);
        AllPayService payService = context.getBean(AllPayService.class);
        payService.doPayAll(100);
    }

}
