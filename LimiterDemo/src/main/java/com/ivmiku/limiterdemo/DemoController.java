package com.ivmiku.limiterdemo;

import com.ivmiku.limiter.annotation.Limiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping
@RestController
public class DemoController {


    /**
     * 默认配置限流演示
     */
    @Limiter(key = "233")
    @GetMapping("hi")
    public String sayHi() {
        return "hi";
    }

    /**
     * 单独配置限流演示
     */
    @Limiter(key = "fullConfig", num = 1, time = 5, timeUnit = TimeUnit.SECONDS)
    @GetMapping("config")
    public String saySuccess() {
        return "success";
    }
}
