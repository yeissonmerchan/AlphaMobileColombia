package com.example.alphamobilecolombia.data.remote.Models;

public class HttpResponse {
    private String Code;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    private String Message;
    private Object Data;

    public String getSendData() {
        return SendData;
    }

    public void setSendData(String sendData) {
        SendData = sendData;
    }

    private String SendData;
}
