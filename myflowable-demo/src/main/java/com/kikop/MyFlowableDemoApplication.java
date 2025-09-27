package com.kikop;

import com.kikop.mapper.UserMapper;
import com.kikop.model.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication
// 替换手动写@Mapper注解
@MapperScan("com.kikop.mapper")
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class MyFlowableDemoApplication implements CommandLineRunner {

    @Autowired
    private UserMapper userMapper;

    public void run(String... args) throws Exception {
        System.out.println("------MyFlowableDemoApplication start------");
    }

    private void testGetUser() {
        User userInfo = userMapper.getById(123456);
        System.out.println("name:" + userInfo.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(MyFlowableDemoApplication.class, args);
    }

}
