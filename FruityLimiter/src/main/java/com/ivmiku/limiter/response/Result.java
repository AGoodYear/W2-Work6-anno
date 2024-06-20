package com.ivmiku.limiter.response;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Result implements Serializable {

    public int code;
    public String message;
    public String timestamp;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        timestamp = LocalDateTime.now().toString();
    }

}
