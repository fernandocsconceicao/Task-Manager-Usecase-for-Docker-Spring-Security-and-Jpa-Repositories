package com.alpha7.alpha7.Test.exception;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor

public class StandardException extends Throwable {
    private static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;

    public StandardException(String msg) {
        super();
        this.status = 500;
        this.msg = msg;
        this.timeStamp =getTimeStamp() ;
    }
    public StandardException(String msg,int status) {
        super();
        this.status = status;
        this.msg = msg;
        this.timeStamp =getTimeStamp() ;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
