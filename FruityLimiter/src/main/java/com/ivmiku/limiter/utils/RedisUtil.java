package com.ivmiku.limiter.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Long setValue(String key, Integer val) {
        Long expire = redisTemplate.opsForValue().getOperations().getExpire(key);

        redisTemplate.opsForValue().set(key, val);

        return expire;

    }

    public boolean ifExist(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setExpire(String key, Integer num, TimeUnit unit) {
        redisTemplate.expire(key,num,unit);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long Decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
