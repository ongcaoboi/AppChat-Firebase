package com.example.appchat_firebase.services;

import com.example.appchat_firebase.UserOj;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FireBaseService {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRootreference;
    private DatabaseReference mChildreference;

    public String idKeyUser;

    public FireBaseService(UserOj user){
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRootreference = firebaseDatabase.getReference();
        idKeyUser = firebaseDatabase.getReference("users").push().getKey();
        mChildreference = mRootreference.child("users").child(idKeyUser);
        mChildreference.setValue(user);
    }

    public FireBaseService(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRootreference = firebaseDatabase.getReference();
    }

    public DatabaseReference getUserDatabaseReference(){
        return mChildreference;
    }

    public void test(){
        Query query = mRootreference.child("users").orderByChild("email").equalTo("tuanAnh_01@gmail.com");
        System.out.println(query);
        System.out.println("_________________________________");
    }
}
