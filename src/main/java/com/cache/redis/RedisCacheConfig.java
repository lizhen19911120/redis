package com.cache.redis;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 13 on 2017/12/4.
 */
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("127.0.0.1");
        config.setPort(6379);
        config.setPassword(RedisPassword.of(""));
        config.setDatabase(2);
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(config);
        return redisConnectionFactory;
    }

//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//        redisTemplate.setConnectionFactory(cf);
//        return redisTemplate;
//    }

    @Bean
    @Qualifier("myRedisCacheConfiguration")
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return redisCacheConfiguration.entryTtl(Duration.ofMinutes(1)).prefixKeysWith("perfect-ssm-cache:");
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory cf,@Qualifier("myRedisCacheConfiguration") RedisCacheConfiguration redisCacheConfiguration) {
        Map<String,RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("myRedisConfig",redisCacheConfiguration);
        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf).withInitialCacheConfigurations(configurationMap).build();
        return cacheManager;
    }





}