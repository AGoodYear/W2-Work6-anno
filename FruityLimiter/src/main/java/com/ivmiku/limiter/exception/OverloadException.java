package com.ivmiku.limiter.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class OverloadException extends RuntimeException{
    int code;
    String date;
    String name;
    String msg;


    public OverloadException(String name, int code) {
        this.name = name;
        this.code=code;
    }

    public OverloadException(String name, int code, String msg) {
        this.name = name;
        this.code=code;
        this.msg=msg;
    }


}
