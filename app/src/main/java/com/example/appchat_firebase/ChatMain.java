package com.example.appchat_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appchat_firebase.services.ChatMainTmp;
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
import java.util.Locale;


public class ChatMain extends Fragment {

    private List<ChatMainTmp> chatMainTmp;
    private List<ChatMainTmp> chatMainTmpOld;
    private List<ChatProcess> arrChatProcess;
    private ChatMainAdapter adapter;
    private ListView lvUser;
    private EditText editSearchChat;

    private DatabaseReference userDatabase;
    private DatabaseReference chatDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_chat_page);
        editSearchChat = (EditText) view.findViewById(R.id.input_search_chat_main);

        chatMainTmp = new ArrayList<>();
        chatMainTmpOld = new ArrayList<>();
        arrChatProcess = new ArrayList<ChatProcess>();

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
                        chatMainTmp.clear();
                        chatMainTmpOld.clear();
                        for (DataSnapshot user : snapshot.getChildren()){
                            UserOj userTmp = user.getValue(UserOj.class);
                            if(userTmp.getId().equals(Global.user.getId())){
                                continue;
                            }
                            for(ChatProcess itemChat : arrChatProcess){
                                if(userTmp.getId().equals(itemChat.getIdUser())){
                                    ChatMainTmp chat_ = new ChatMainTmp(userTmp, itemChat);
                                    chatMainTmp.add(chat_);
                                    break;
                                }
                            }
                        }
                        chatMainTmp.sort((o1, o2) -> {
                            if(o1.getChatInfo().getMessageNew().getTime() == o2.getChatInfo().getMessageNew().getTime())
                                return 0;
                            return o1.getChatInfo().getMessageNew().getTime() < o2.getChatInfo().getMessageNew().getTime() ? 1 : -1;
                        });
                        chatMainTmpOld = chatMainTmp;
                        setAdapter(chatMainTmp);
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

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), Message.class);
                intent.putExtra("idChat", chatMainTmp.get(i).getChatInfo().getIdChat());
                intent.putExtra("idUserChat", chatMainTmp.get(i).getUser().getId());
                startActivity(intent);
            }
        });

        editSearchChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = editSearchChat.getText().toString().toLowerCase(Locale.ROOT);
                search(value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
    private void search(String text){
        if(text.isEmpty()){
            setAdapter(chatMainTmpOld);
            return;
        }
        List<ChatMainTmp> listChatTmp = new ArrayList<>();
        for(ChatMainTmp chat : chatMainTmp){
            if(chat.getUser().getFirstName().toLowerCase(Locale.ROOT).contains(text) || chat.getUser().getLastName().toLowerCase(Locale.ROOT).contains(text)){
                listChatTmp.add(chat);
            }
        }
        setAdapter(listChatTmp);
    }
    private void setAdapter(List list){
        adapter = new ChatMainAdapter(getContext(),R.layout.item_chatpage,list);
        lvUser.setAdapter(adapter);
    }
}