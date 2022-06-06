package com.example.appchat_firebase.services;

import com.example.appchat_firebase.UserOj;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseService {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRootreference;
    private DatabaseReference mChildreference;

    public FireBaseService(UserOj user){
        firebaseDatabase = FirebaseDatabase.getInstance();
        mRootreference = firebaseDatabase.getReference();
        mChildreference = mRootreference.child("users").child("_123491");
        mChildreference.setValue(user);
    }

    public DatabaseReference getUserDatabaseReference(){
        return mChildreference;
    }
}
