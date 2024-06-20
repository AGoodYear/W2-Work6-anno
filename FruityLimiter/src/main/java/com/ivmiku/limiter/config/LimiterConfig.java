package com.ivmiku.limiter.config;

import com.ivmiku.limiter.aspect.FruityLimiter;
import com.ivmiku.limiter.properties.LimiterProperties;
import com.ivmiku.limiter.response.Result;
import com.ivmiku.limiter.utils.RedisUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aurora
 */
@Configuration
@ComponentScan("com.ivmiku.limiter")
@EnableConfigurationProperties(LimiterProperties.class)
public class LimiterConfig {
    @Bean
    public FruityLimiter fruityLimiter() {
        return new FruityLimiter();
    }

    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

    @Bean
    public Result result() {
        return new Result(100, "default");
    }
}
