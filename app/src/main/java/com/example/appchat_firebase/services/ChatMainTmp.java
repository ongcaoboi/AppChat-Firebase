package com.example.appchat_firebase.services;

import com.example.appchat_firebase.UserOj;

public class ChatMainTmp {
    private UserOj user;
    private ChatProcess chatInfo;

    public ChatMainTmp(UserOj user, ChatProcess chatInfo) {
        this.user = user;
        this.chatInfo = chatInfo;
    }

    public UserOj getUser() {
        return user;
    }

    public void setUser(UserOj user) {
        this.user = user;
    }

    public ChatProcess getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(ChatProcess chatInfo) {
        this.chatInfo = chatInfo;
    }
}
