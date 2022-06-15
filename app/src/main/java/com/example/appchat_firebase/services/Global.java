package com.example.appchat_firebase.services;

import android.content.Context;

import com.example.appchat_firebase.UserOj;

public class Global {
    public static UserOj user;
    public static DBManager dbManager;

    public Global(Context context){
        dbManager = new DBManager(context);
    }
    public static void login(UserOj user1){
        dbManager.addUser(user1);
        user = user1;
    }
    public static void logout(){
        dbManager.Delete(user);
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
        return true;
    }
}
