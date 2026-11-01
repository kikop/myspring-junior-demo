package com.xxxkikop.conf;


import com.xxxkikop.model.WelcomeProperties;
import com.xxxkikop.service.WelcomeService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author kikop
 * @version 1.0
 * @project xxx-spring-boot-starter
 * @file WelcomeConfig
 * @desc 控制 Bean 加载条件 + 注册 Bean
 * @date 2025/7/19
 * @time 7:37
 * @by IDE: IntelliJ IDEA
 */
// Configuration标志位配置类
@Configuration
// 配置属性绑定
@EnableConfigurationProperties(WelcomeProperties.class)
// 确保业务包中进入了自定义xxxstarger,引入即可自动触发
@ConditionalOnClass(WelcomeService.class)
public class WelcomeConfig {

    @Bean
    @ConditionalOnMissingBean
    public WelcomeService welcomeService(WelcomeProperties welcomeProperties) {
        return new WelcomeService(welcomeProperties);
    }
}
