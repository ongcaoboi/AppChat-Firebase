package com.example.appchat_firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchat_firebase.services.ChatTmp;
import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatMain extends Fragment {

    private List<UserOj> arrayUser;
    private ChatMainAdapter adapter;
    private ListView lvUser;

    private List<String> arrChatUser;
    private DatabaseReference userDatabase;
    private DatabaseReference chatDatabase;
    private List<String> messageEnds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_chat_page);
        arrayUser = new ArrayList<UserOj>();
        arrChatUser = new ArrayList<String>();
        adapter = new ChatMainAdapter(container.getContext(),R.layout.item_chatpage,arrayUser);

        chatDatabase = FirebaseDatabase.getInstance().getReference().child("chats");
        chatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrChatUser.clear();
                for(DataSnapshot chat : snapshot.getChildren()){
                    String key = chat.getKey();
                    ChatTmp chatTmp = chat.getValue(ChatTmp.class);
                    if(chatTmp.getUser_1().equals(Global.user.getId())){
                        arrChatUser.add(chatTmp.getUser_2());
                    }else if(chatTmp.getUser_2().equals(Global.user.getId())){
                        arrChatUser.add(chatTmp.getUser_1());
                    }
                }
                userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                userDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayUser.clear();
                        for (DataSnapshot user : snapshot.getChildren()){
                            UserOj userTmp = user.getValue(UserOj.class);
                            if(userTmp.getId().equals(Global.user.getId())){
                                continue;
                            }
                            for(String item : arrChatUser){
                                if(userTmp.getId().equals(item)){

                                    arrayUser.add(userTmp);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Get list user failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list user failed!", Toast.LENGTH_SHORT).show();
            }
        });

        lvUser.setAdapter(adapter);
        return view;
    }
}