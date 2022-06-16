package com.example.appchat_firebase.services;

import android.content.Context;
import com.example.appchat_firebase.UserOj;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Global {
    public static UserOj user;
    public static DBManager dbManager;
    public static String idChatOnOpen;
    public static List<ChatMainTmp> chats;
    public static boolean ACTION_FOREGROUND = false;

    public Global(Context context){
        dbManager = new DBManager(context);
    }
    public static void login(UserOj user1){
        dbManager.addUser(user1);
        user = user1;
        ACTION_FOREGROUND = true;
    }
    public static void logout(){
        idChatOnOpen = null;
        chats = null;
        ACTION_FOREGROUND = false;
        dbManager.Delete(user);
        FirebaseDatabase.getInstance().getReference("users").child(user.getId()).child("trangThai").setValue(false);
        user = null;
    }
    public static void update(UserOj user1){
        dbManager.Update(user1);
        user = user1;
    }
    public static boolean isLogin(){
        UserOj user_ = dbManager.getUser();
        if(user_ == null){
            return false;
        }
        user = user_;
        ACTION_FOREGROUND = true;
        return true;
    }
}
