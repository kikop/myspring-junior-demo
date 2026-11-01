package com.kikop3;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class MyFirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {

        return args -> {

            // Start the chat loop
            System.out.println("\nI am your assistant.\n");

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    System.out.print("\nUSER: ");
                    System.out.println("\nASSISTANT: " + scanner.nextLine());
                }
            }


        };
    }

}
