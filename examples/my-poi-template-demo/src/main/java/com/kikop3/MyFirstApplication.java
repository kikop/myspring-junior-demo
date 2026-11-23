package com.kikop3;


import com.kikop3.demo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyFirstApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        MyLoopRowTableRenderPolicyDemo.testRender();

        // docx模板（同一行）
        MyLoopRowTableDefaultRenderPolicyDemo.testRender_same(true);


        // MyQuickDemo.testRender();
        //MyQuickDataModelDemo.testRender();
        // MyTextRenderDataDemo.testRender();
        //MyPictureRenderDataDemo.testRender();
        // MyTableRenderDataDemo.testRender();
        // MySpringExpLangDemo.testRender();
        // MyDocxAttachDemo.testRender();
        // MyMarkdownForDocxDemo.testRender();
//        MyNoTemplateRenderDataDemo.testRender();

    }
}
