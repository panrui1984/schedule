package com.yakx.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * 排除路径
     */
    private String[] excludes;

}
