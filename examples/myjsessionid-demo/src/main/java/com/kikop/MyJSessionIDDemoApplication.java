package com.kikop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyJSessionIDDemoApplication implements CommandLineRunner {

    public void run(String... args) throws Exception {
        System.out.println("------MyJSessionIDDemoApplication start------");
    }

    public static void main(String[] args) {
        SpringApplication.run(MyJSessionIDDemoApplication.class, args);
    }

}
