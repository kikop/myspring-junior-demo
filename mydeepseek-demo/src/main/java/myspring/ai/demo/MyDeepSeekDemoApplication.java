package myspring.ai.demo;

import myspring.ai.demo.service.DeepSeekByChatClientService;
import myspring.ai.demo.service.DeepSeekByOkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyDeepSeekDemoApplication implements CommandLineRunner {

    @Autowired
    private DeepSeekByChatClientService deepSeekByChatClientService;

    @Autowired
    private DeepSeekByOkHttpClient deepSeekByOkHttpClient;

    public static void main(String[] args) {
        // 本质:return (new SpringApplication(primarySources)).run(args);
        SpringApplication.run(MyDeepSeekDemoApplication.class, args);
    }

    public void run(String... args) throws Exception {
        System.out.println("------MyDeepSeekDemoApplication start------");
        deepSeekByOkHttpClient.testChatCompletion();
    }

    public void testAll(){
//        deepSeekByChatClientService.runByChatClient();

    }

}
