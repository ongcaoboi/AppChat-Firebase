package com.example.appchat_firebase.services;

public class ChatTmp {
    private String user_1, user_2;

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    private Object messages;

    public String getUser_1() {
        return user_1;
    }

    public void setUser_1(String user_1) {
        this.user_1 = user_1;
    }

    public String getUser_2() {
        return user_2;
    }

    public void setUser_2(String user_2) {
        this.user_2 = user_2;
    }

    public ChatTmp(){}

    public ChatTmp(String user_1, String user_2) {
        this.user_1 = user_1;
        this.user_2 = user_2;
    }

    public ChatTmp(String user_1, String user_2, Object messages) {
        this.user_1 = user_1;
        this.user_2 = user_2;
        this.messages = messages;
    }

    @Override
    public String toString(){
        return "user_1: "+this.user_1 +"\nuser_2: "+user_2;
    }

}
