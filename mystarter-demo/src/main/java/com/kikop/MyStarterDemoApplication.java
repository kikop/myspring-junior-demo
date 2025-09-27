package com.kikop;

import com.xxxkikop.service.XxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class MyStarterDemoApplication implements CommandLineRunner {


    @Autowired
    private XxxService xxxService;


    public void run(String... args) throws Exception {
        System.out.println("------MyStarterDemoApplication start------");
        //xxxService.doSomeThing();
    }


    public static void main(String[] args) {
        SpringApplication.run(MyStarterDemoApplication.class, args);
    }

}
