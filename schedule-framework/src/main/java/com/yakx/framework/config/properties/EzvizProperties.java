package com.yakx.framework.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ezviz")
public class EzvizProperties {

    private String accessTokenUrl;

    private String appKey;

    private String appSecret;
}
