package com.example.appchat_firebase.services;

import com.example.appchat_firebase.MessageOj;

public class ChatProcess {
    private String idChat, idUser;
    private MessageOj messageNew;

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public MessageOj getMessageNew() {
        return messageNew;
    }

    public void setMessageEnd(MessageOj messageNew) {
        this.messageNew = messageNew;
    }

    public ChatProcess(String idChat, String idUser, MessageOj messageNew) {
        this.idChat = idChat;
        this.idUser = idUser;
        this.messageNew = messageNew;
    }
}
