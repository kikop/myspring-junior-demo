package myspring.ai.demo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Data
//将配置文件中以zjqProp开头的属性通过setXxx方法注入到该类的属性中
@ConfigurationProperties(prefix = "spring.ai.openai")
//把PropertiesConfig类作为Bean到spring容器中，只有这样才能@ConfigurationProperties注解进行赋值
@Component
public class DeepSeekMeta2Info {
    private String api_key;
    private String base_url;


    // 保持原配置不变，通过 @ConfigurationProperties 的宽松绑定特性实现映射
    @NestedConfigurationProperty
    private ChatOptions chat = new ChatOptions();

    @Data
    public static class ChatOptions {
        private Options options=new Options();
    }


    @Data
    public static class Options {
        private String model;
    }

}

