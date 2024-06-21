package com.ivmiku.limiter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("flimiter")
public class LimiterProperties {
    private int method = 1;

    private int errorCode = -1;

    private String errorException = "";

    private int defaultTime =1000;

    private int defaultNum =10;
}
