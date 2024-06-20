package com.ivmiku.limiterdemo;

import com.ivmiku.limiter.annotation.Limiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RequestMapping
@RestController
public class DemoController {



    @Limiter(key = "233", time = 5, num = 2, timeUnit = TimeUnit.SECONDS)
    @GetMapping("hi")
    public String sayHi() {
        return "hi";
    }
}
