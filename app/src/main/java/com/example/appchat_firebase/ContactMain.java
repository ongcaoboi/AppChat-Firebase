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

import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactMain extends Fragment {

    private List<UserOj> arrayUser;
    private List<UserOj> arrayUserOld;
    private UserAdapter adapter;
    public ListView lvUser;
    private EditText editSearchContact;

    private DatabaseReference userDatabase;

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_contact);
        editSearchContact = (EditText) view.findViewById(R.id.input_search_contact);
        arrayUser = new ArrayList<UserOj>();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayUser.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserOj user = dataSnapshot.getValue(UserOj.class);
                    if(user.getId().equals(Global.user.getId())){
                        continue;
                    }
                    arrayUser.add(user);
                }
                arrayUserOld = arrayUser;
                setAdapter(container, arrayUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list user failed!", Toast.LENGTH_SHORT).show();
            }
        });

        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), ActivityUserInfo.class);
                UserOj user = arrayUser.get(i);
                intent.putExtra("name", user.getFirstName()+" "+user.getLastName());
                String gender = "male";
                String sdt = user.getSdt();
                if(!user.isGioiTinh()) gender = "female";
                String status = "offline";
                if(user.isTrangThai()) status = "online";
                intent.putExtra("gender", gender);
                intent.putExtra("status", status);
                intent.putExtra("sdt", sdt);
                intent.putExtra("id", user.getId());
                startActivity(intent);
            }
        });

        editSearchContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = editSearchContact.getText().toString().toLowerCase(Locale.ROOT);
                search(container, value);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lvUser.setAdapter(adapter);
        return view;
    }
    private void search(ViewGroup container, String text){
        if(text.isEmpty()){
            setAdapter(container, arrayUserOld);
            return;
        }
        List<UserOj> listUserTmp = new ArrayList<>();
        for(UserOj user : arrayUser){
            if(user.getFirstName().toLowerCase(Locale.ROOT).contains(text) || user.getLastName().toLowerCase(Locale.ROOT).contains(text)){
                listUserTmp.add(user);
            }
        }
        setAdapter(container, listUserTmp);
    }
    private void setAdapter(ViewGroup container, List list){
        adapter = new UserAdapter(container.getContext(),R.layout.item_contact,list);
        lvUser.setAdapter(adapter);
    }
}