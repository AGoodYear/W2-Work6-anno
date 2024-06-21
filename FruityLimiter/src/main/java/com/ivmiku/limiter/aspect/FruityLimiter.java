package com.ivmiku.limiter.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ivmiku.limiter.annotation.Limiter;
import com.ivmiku.limiter.properties.LimiterProperties;
import com.ivmiku.limiter.response.Result;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import com.ivmiku.limiter.exception.OverloadException;
import com.ivmiku.limiter.utils.RedisUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class FruityLimiter {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private LimiterProperties limiterProperties;

    @Around("@annotation(com.ivmiku.limiter.annotation.Limiter)")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limiter limiter = method.getAnnotation(Limiter.class);
        if (limiter!=null) {
            String key = "limiter:" + limiter.key();
            int time, limitNum;
            if (limiter.time()!=-1) {
                time = limiter.time();
            } else {
                time = limiterProperties.getDefaultTime();
            }
            if (limiter.num()!=-1) {
                limitNum = limiter.num();
            } else {
                limitNum = limiterProperties.getDefaultNum();
            }
            if (limiterProperties.getMethod()==1) {
                if (redisUtil.ifExist(key)) {
                    Integer num = Integer.parseInt(redisUtil.getValue(key)) ;
                    if (num >0) {
                        Long expire = redisUtil.setValue(key, num-1);
                        redisUtil.setExpire(key, Math.toIntExact(expire), TimeUnit.SECONDS);
                    } else {
                        throw new OverloadException(limiter.key(), limiterProperties.getErrorCode(), limiterProperties.getErrorException());
                    }
                } else {
                    redisUtil.setValue(key, limitNum-1);
                    redisUtil.setExpire(key, time, limiter.timeUnit());
                }
            } else if (limiterProperties.getMethod()==2) {
                if (redisUtil.ifExist(key)) {
                    Integer num = Integer.parseInt(redisUtil.getValue(key));
                    if (!redisUtil.ifExist(key+"s")) {
                        redisUtil.setValue(key, num-1+limiter.num());
                        redisUtil.setValue(key+"s", 0);
                        redisUtil.setExpire(key+"s", limiter.time(), limiter.timeUnit());
                    }
                    if (num >0) {
                        redisUtil.setValue(key, num-1);
                    } else {
                        throw new OverloadException(limiter.key(), limiterProperties.getErrorCode());
                    }
                } else {
                    redisUtil.setValue(key, limiter.num()-1);
                    redisUtil.setValue(key+"s", 0);
                    redisUtil.setExpire(key+"s", limiter.time(), limiter.timeUnit());
                }
            }
        }
        return joinPoint.proceed();
    }



}

@RestControllerAdvice
class Call{
    @ExceptionHandler(OverloadException.class)
    @ResponseBody
    public Object error(OverloadException e) throws JsonProcessingException {
        int code = e.getCode();
        String msg = e.getName();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        Result result;
        if (e.getMsg().equals("")) {
            result = new Result(code, "接口" + msg + "已被限流");
        } else {
            result = new Result(code, e.getMsg());
        }
        return objectMapper.writeValueAsString(result);
    }
}
