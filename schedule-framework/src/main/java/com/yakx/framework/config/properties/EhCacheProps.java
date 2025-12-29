package com.yakx.framework.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
@ConfigurationProperties(prefix = "ehcache")
public class EhCacheProps {

    private int heap;

    private int offHeap;

    private int disk;

    private String diskDir;

    private Set<String> cacheName;

}
