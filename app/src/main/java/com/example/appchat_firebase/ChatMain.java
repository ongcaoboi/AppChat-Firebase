package com.example.appchat_firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class ChatMain extends Fragment {

    private List<UserOj> arrayUser;
    private ChatMainAdapter adapter;
    private ListView lvUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_chat_page);
        arrayUser = new ArrayList<UserOj>();
        arrayUser.add(new UserOj("01", "tule@gmail.com","Tụ", "Lê","123123123",true,true));
        arrayUser.add(new UserOj("02", "tuananh@email.com","Tuấn Anh", "Hứa","1231231231233",false,false));


        adapter = new ChatMainAdapter(container.getContext(),R.layout.item_chatpage,arrayUser);
        lvUser.setAdapter(adapter);
        return view;
    }
}