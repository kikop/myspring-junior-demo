package com.kikop;

import com.xxxkikop.service.WelcomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyWelcomeStarterDemoApplication implements CommandLineRunner {


    @Autowired
    private WelcomeService welcomeService;


    public void run(String... args) throws Exception {
        System.out.println("------MyWelcomeStarterDemoApplication start------");
        log.info("【MyWelcomeStarterDemoApplication】tagName:{}",welcomeService.getTagName());
    }


    public static void main(String[] args) {
        SpringApplication.run(MyWelcomeStarterDemoApplication.class, args);
    }

}
