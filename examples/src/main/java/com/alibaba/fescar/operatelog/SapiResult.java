package com.alibaba.fescar.operatelog;

/**
 * @author: wenyixicodedog
 * @create: 2021-01-22
 * @description: SapiResult
 */
public class SapiResult<T> {

    private String status;

    private String message;

    private T data;

    public SapiResult() {
    }

    public SapiResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public SapiResult(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
