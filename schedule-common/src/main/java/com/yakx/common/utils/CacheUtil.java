package com.yakx.common.utils;


import com.yakx.common.utils.spring.SpringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.cache.Cache;

/**
 * 缓存操作工具类 {@link }
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings(value = {"unchecked"})
public class CacheUtil {

    private static final CacheManager CACHE_MANAGER = SpringUtils.getBean("cacheManager");

    /**
     * 获取缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    public static <T> T get(String cacheNames, String key) {
        Cache cache = CACHE_MANAGER.getCache(cacheNames);
        return cache.get(key) != null ? (T) cache.get(key).get() : null;
    }

    /**
     * 保存缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     * @param value      缓存值
     */
    public static void put(String cacheNames, String key, Object value) {
        Cache cache = CACHE_MANAGER.getCache(cacheNames);
        cache.put(key, value);
    }

    /**
     * 删除缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    public static void evict(String cacheNames, Object key) {
        CACHE_MANAGER.getCache(cacheNames).evict(key);
    }

    /**
     * 清空缓存值
     *
     * @param cacheNames 缓存组名称
     */
    public static void clear(String cacheNames) {
        CACHE_MANAGER.getCache(cacheNames).clear();
    }

}
