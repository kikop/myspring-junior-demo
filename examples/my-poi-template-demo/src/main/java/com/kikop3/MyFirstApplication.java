package com.kikop3;


import com.kikop3.demo1.LoopRowTableRenderPolicyDemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class MyFirstApplication  implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(MyFirstApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LoopRowTableRenderPolicyDemo.testRender();
    }
}
