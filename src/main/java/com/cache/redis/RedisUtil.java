//package com.cache.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.cache.RedisCache;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * Created by 13 on 2017/12/4.
// */
//@Component
//public class RedisUtil extends RedisCacheManager {
//
//    @Autowired
//    private RedisCacheManager redisCacheManager;
//
//    public RedisUtil(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
//        super(cacheWriter, defaultCacheConfiguration);
//    }
//
//    public RedisUtil(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
//        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
//    }
//
//    public RedisUtil(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
//        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
//    }
//
//    public RedisUtil(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
//        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
//    }
//
//    public RedisUtil(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
//        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
//    }
//
//    public void put(String key, Object obj) {
//        cache.put(key, obj);
//    }
//
//    public Object get(String key, Class clazz) {
//        return cache.get(key) == null ? null : cache.get(key, clazz);
//    }
//
//    public void del(String key) {
//        cache.evict(key);
//    }
//}