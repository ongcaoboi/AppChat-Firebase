package com.example.appchat_firebase;

public class MessageOj {

    private String msg, userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private int status;

    private long time;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }

    public MessageOj(String message, String userId, long time, int status) {
        this.msg = message;
        this.userId = userId;
        this.time = time;
        this.status = status;
    }

    public MessageOj(){}
}
