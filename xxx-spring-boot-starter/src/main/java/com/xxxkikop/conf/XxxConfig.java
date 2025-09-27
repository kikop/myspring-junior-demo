package com.xxxkikop.conf;

import com.xxxkikop.model.XxxProperties;
import com.xxxkikop.service.XxxService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author kikop
 * @version 1.0
 * @project xxx-spring-boot-starter
 * @file XxxConfig
 * @desc 控制 Bean 加载条件 + 注册 Bean
 * @date 2025/7/19
 * @time 7:37
 * @by IDE: IntelliJ IDEA
 */
// Configuration标志位配置类
@Configuration
// 应用配置属性绑定
@EnableConfigurationProperties(XxxProperties.class)
// 确保业务包中进入了自定义xxxstarger,引入即可自动触发
@ConditionalOnClass(XxxService.class)
public class XxxConfig {

//    @Autowired
//    private XxxProperties xxxProperties;

    @Bean
    @ConditionalOnMissingBean
    public XxxService xxxService(XxxProperties xxxProperties) {
        return new XxxService(xxxProperties);
    }
}
