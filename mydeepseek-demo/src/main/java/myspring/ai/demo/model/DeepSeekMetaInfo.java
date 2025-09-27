package myspring.ai.demo.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
//将配置文件中以zjqProp开头的属性通过setXxx方法注入到该类的属性中
@ConfigurationProperties(prefix = "spring.ai.openai")
//把PropertiesConfig类作为Bean到spring容器中，只有这样才能@ConfigurationProperties注解进行赋值
@Component
public class DeepSeekMetaInfo {
    
    private String api_key;
    private String base_url;
    
    // 这里单独读取配置
    @Value("${spring.ai.openai.chat.options.model}")
    private String model;
}

