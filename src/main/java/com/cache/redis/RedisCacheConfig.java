package com.cache.redis;


import com.cache.common.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
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
 * Enables Spring's annotation-driven cache management capability
 * For those that wish to establish a more direct relationship between @EnableCaching and the exact cache manager bean to be used, the CachingConfigurer callback interface may be implemented.
 * 如果想在@EnableCaching和使用的cache manager之间建立更明确的关系，CachingConfigurer这个回调接口应该被实现（这里通过继承CachingConfigurerSupport）
 * @EnableCaching+@Configuration+CachingConfigurerSupport 指明了更多样化、更灵活的缓存机制。
 * Created by 13 on 2017/12/4.
 */
@EnableCaching
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 配置缓存使用的redis连接工厂,这里使用JedisConnectionFactory实现,类似可以使用LettuceConnectionFactory
     * 使用jedis：当多线程使用同一个连接时，是线程不安全的。所以要使用连接池，为每个jedis实例分配一个连接。
     * 使用Lettuce：当多线程使用同一连接实例时，是线程安全的。
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("127.0.0.1");
        config.setPort(6379);
        config.setPassword(RedisPassword.of(""));
        config.setDatabase(1);
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(config);
        return redisConnectionFactory;
    }

    /**
     * 配置redis的具体特征，比如这里设置缓存时限3分钟，key前缀为perfect-ssm-cache
     * @return
     */
    @Bean
    @Qualifier("myRedisCacheConfiguration")
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return redisCacheConfiguration.entryTtl(Duration.ofMinutes(3)).prefixKeysWith("perfect-ssm-cache:");
    }

    /**
     * 配置@Cacheable等缓存注解生效时的key的生成匹配策略，这里设置为key为"perfect-ssm:article"+应用缓存的方法的第一个参数
     * @return
     */
    @Bean
    @Qualifier("mykeyGenerator")
    public KeyGenerator keyGenerator(){
        return (t,m,p) -> Constants.ARTICLE_CACHE_KEY +p[0];
    }

    /**
     * 配置redis缓存管理器，将连接工厂、redis配置器放入其中，如果有多个配置器，则这个管理器会有多个不同的cache实例，可以在项目中按需使用不同的cache实例
     * 另外还有其他管理器，诸如SimpleCacheManager——一个自定义的简单cache管理器，ConcurrentMapCacheManager——使用ConcurrentMapCache这个cache实例的管理器，底层是ConcurrentMap
     * @param cf
     * @param redisCacheConfiguration
     * @return
     */
    @Bean
//    @Primary //如果有多个cache manager，则必须指定一个manager作为主要的,但是如果配置CacheResolver，并在@Cacheable等注解上指明了CacheResolver,则可以不加@Primary
    public RedisCacheManager cacheManager(RedisConnectionFactory cf,@Qualifier("myRedisCacheConfiguration") RedisCacheConfiguration redisCacheConfiguration) {
        Map<String,RedisCacheConfiguration> configurationMap = new HashMap<>();
        configurationMap.put("myRedisConfig",redisCacheConfiguration);//"myRedisConfig"就是一个cache实例的name
        RedisCacheManager cacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf).withInitialCacheConfigurations(configurationMap).build();
        return cacheManager;
    }

    /**
     * 配置一个使用ConcurrentMap作为缓存底层实现的cache manager
     * @return
     */
    @Bean
    public ConcurrentMapCacheManager mapCacheManager(){
        return new ConcurrentMapCacheManager("myConMapManager");
    }

    /**
     * 将某个cache manager绑定到某个CacheResolver，可以配置多个CacheResolver
     * @return
     */
    @Bean
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(mapCacheManager());
    }


}