package com.shtf.edu.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenlingyu
 */
@Data
@ConfigurationProperties(prefix = "spring.async")
public class EnableAsyncProperties {
    private  int corePoolSize;
    private  int maxPoolSize;
    private  int queueCapacity;
    private  int keepAliveSeconds;
    private  String threadNamePrefix;
}

