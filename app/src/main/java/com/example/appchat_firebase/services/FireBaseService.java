package com.example.appchat_firebase.services;

import com.example.appchat_firebase.UserOj;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public DatabaseReference getUserDatabaseReference(){
        return mChildreference;
    }
}
