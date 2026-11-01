package com.xxxkikop;

import com.xxxkikop.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckDemoApplication implements CommandLineRunner {

    @Autowired
    private WelcomeService welcomeService;

    public static void main(String[] args) {
        SpringApplication.run(CheckDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(welcomeService.getTagName());
    }
}
