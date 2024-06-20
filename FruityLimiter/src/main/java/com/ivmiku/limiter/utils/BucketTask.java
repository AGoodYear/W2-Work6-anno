//package com.ivmiku.limiter.utils;
//
//import jakarta.annotation.Resource;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ConcurrentHashMap;
//
//@EnableScheduling
//@Component
//public class BucketTask implements SchedulingConfigurer {
//    @Resource
//    private RedisUtil redisUtil;
//
//    public static ConcurrentHashMap<String, Object> taskMap = new ConcurrentHashMap<>();
//
//    public static void addKey(String key, String time) {
//        taskMap.put(key, time);
//    }
//
//    public void addBucket(String key, Integer num, Integer maxAmount) {
//        Integer amount = (Integer) redisUtil.getValue(key);
//        if (amount <= maxAmount) {
//            redisUtil.setValue(key, amount+num);
//        }
//
//    }
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addTriggerTask(() -> addBucket(),
//                triggerContext -> {
//
//                });
//    }
//}
