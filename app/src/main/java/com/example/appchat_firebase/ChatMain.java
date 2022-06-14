package com.example.appchat_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchat_firebase.services.ChatProcess;
import com.example.appchat_firebase.services.ChatTmp;
import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ChatMain extends Fragment {

    private List<UserOj> arrayUser;
    private ChatMainAdapter adapter;
    private ListView lvUser;

    private DatabaseReference userDatabase;
    private DatabaseReference chatDatabase;
    private List<ChatProcess> arrChatProcess;
    private List<ChatProcess> arrChatInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_chat_page);
        arrayUser = new ArrayList<UserOj>();
        arrChatProcess = new ArrayList<ChatProcess>();
        arrChatInfo = new ArrayList<ChatProcess>();
        adapter = new ChatMainAdapter(container.getContext(),R.layout.item_chatpage,arrayUser, arrChatInfo);

        chatDatabase = FirebaseDatabase.getInstance().getReference().child("chats");
        chatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrChatProcess.clear();
                for(DataSnapshot chat : snapshot.getChildren()){
                    String key = chat.getKey();
                    ChatTmp chatTmp = chat.getValue(ChatTmp.class);
                    List<MessageOj> messages = new ArrayList<MessageOj>();
                    for(DataSnapshot message : chat.child("messages").getChildren()){
                        MessageOj messageOj = message.getValue(MessageOj.class);
                        messages.add(messageOj);
                    }
                    messages.sort(Comparator.comparing(MessageOj::getTime).reversed());
                    if(!messages.isEmpty()){

                        if(chatTmp.getUser_1().equals(Global.user.getId())){
                            ChatProcess chatP = new ChatProcess(key, chatTmp.getUser_2(), messages.get(0));
                            arrChatProcess.add(chatP);
                        }else if(chatTmp.getUser_2().equals(Global.user.getId())){
                            ChatProcess chatP = new ChatProcess(key, chatTmp.getUser_1(), messages.get(0));
                            arrChatProcess.add(chatP);
                        }
                    }
                }
                userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                userDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayUser.clear();
                        arrChatInfo.clear();
                        for (DataSnapshot user : snapshot.getChildren()){
                            UserOj userTmp = user.getValue(UserOj.class);
                            if(userTmp.getId().equals(Global.user.getId())){
                                continue;
                            }
                            for(ChatProcess itemChat : arrChatProcess){
                                if(userTmp.getId().equals(itemChat.getIdUser())){
                                    arrayUser.add(userTmp);
                                    arrChatInfo.add(itemChat);
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

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), Message.class);
                intent.putExtra("idChat", arrChatInfo.get(i).getIdChat());
                intent.putExtra("idUserChat", arrayUser.get(i).getId());
                startActivity(intent);
            }
        });
        return view;
    }
}