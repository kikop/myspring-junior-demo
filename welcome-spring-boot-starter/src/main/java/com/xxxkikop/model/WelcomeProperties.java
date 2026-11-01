package com.xxxkikop.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.starter")
public class WelcomeProperties {
    private String name;
}
