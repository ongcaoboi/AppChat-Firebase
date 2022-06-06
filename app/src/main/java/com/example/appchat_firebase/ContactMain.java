package com.example.appchat_firebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ContactMain extends Fragment {

    private List<UserOj> arrayUser;
    private UserAdapter adapter;
    private ListView lvUser;

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_contact);
        arrayUser = new ArrayList<UserOj>();
        arrayUser.add(new UserOj("01", "tule@gmail.com","Tụ", "Lê","123123123",true,true));
        arrayUser.add(new UserOj("02", "tuananh@email.com","Tuấn Anh", "Hứa","1231231231233",false,false));

        adapter = new UserAdapter(container.getContext(),R.layout.item_contact,arrayUser);
        lvUser.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }


}