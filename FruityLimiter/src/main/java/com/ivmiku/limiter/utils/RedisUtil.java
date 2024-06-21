package com.ivmiku.limiter.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Long setValue(String key, Integer val) {
        Long expire = stringRedisTemplate.opsForValue().getOperations().getExpire(key);

        stringRedisTemplate.opsForValue().set(key, String.valueOf(val));

        return expire;

    }

    public boolean ifExist(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void setExpire(String key, Integer num, TimeUnit unit) {
        stringRedisTemplate.expire(key,num,unit);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Long Decrement(String key) {
        return stringRedisTemplate.opsForValue().decrement(key);
    }
}
