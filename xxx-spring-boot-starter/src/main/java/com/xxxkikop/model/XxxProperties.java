package com.xxxkikop.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "your.starter")
public class XxxProperties {
    private String name;
}
