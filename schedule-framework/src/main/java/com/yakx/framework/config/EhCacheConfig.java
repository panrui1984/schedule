package com.yakx.framework.config;

import com.yakx.framework.config.properties.EhCacheProps;
import lombok.RequiredArgsConstructor;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.ExpiryPolicy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@EnableCaching
public class EhCacheConfig {


}

