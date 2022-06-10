package com.example.appchat_firebase;


import java.util.Comparator;

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

    public static Comparator<MessageOj> compare = new Comparator<MessageOj>(){
        @Override
        public int compare(MessageOj m1, MessageOj m2) {
            return m1.time > m2.time ? 1 : 0;
        }
    };
}
