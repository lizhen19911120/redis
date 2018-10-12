package com.fei.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration
//maxInactiveIntervalInSeconds(会话请求最大间隔时间，非redis存储时间) 默认是1800秒过期，这里测试修改为60秒
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60)
public class RedisSessionConfig extends RedisHttpSessionConfiguration {

}
