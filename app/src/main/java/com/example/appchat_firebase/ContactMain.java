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

public class ContactMain extends Fragment {

    private List<UserOj> arrayUser;
    private UserAdapter adapter;
    public ListView lvUser;

    private DatabaseReference userDatabase;

    @Override
    @NonNull
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_main, container, false);
        lvUser = (ListView) view.findViewById(R.id.lv_contact);
        arrayUser = new ArrayList<UserOj>();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");
        adapter = new UserAdapter(container.getContext(),R.layout.item_contact,arrayUser);
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
                adapter.notifyDataSetChanged();
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
                if(!user.isGioiTinh()) gender = "female";
                String status = "offline";
                if(user.isTrangThai()) status = "online";
                intent.putExtra("gender", gender);
                intent.putExtra("status", status);
                intent.putExtra("id", user.getId());
                startActivity(intent);

//                DatabaseReference dbContact = FirebaseDatabase.getInstance().getReference("chats");
//                dbContact.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String idChat = "";
//                        for(DataSnapshot chat : snapshot.getChildren()){
//                            String key = chat.getKey();
//                            ChatTmp chatTmp = chat.getValue(ChatTmp.class);
//                            if(chatTmp.getUser_1().equals(Global.user.getId())){
//                                if(chatTmp.getUser_2().equals(arrayUser.get(i))){
//                                    idChat = key;
//                                }
//                            }else if(chatTmp.getUser_2().equals(Global.user.getId())){
//                                if(chatTmp.getUser_1().equals(arrayUser.get(i))){
//                                    idChat = key;
//                                }
//                            }
//                        }
//                        if(idChat.equals("")){
//                            idChat = dbContact.push().getKey();
//                            ChatTmp chatTmp = new ChatTmp(Global.user.getId(), arrayUser.get(i).getId(), null);
//                            dbContact.child(idChat).setValue(chatTmp);
//                        }
//                        Intent intent = new Intent(getContext(), Message.class);
//                        intent.putExtra("idChat", idChat);
//                        intent.putExtra("idUserChat", arrayUser.get(i).getId());
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                Intent intent = new Intent(getContext(), Message.class);
//                UserOj user = arrayUser.get(i);
//                intent.putExtra("id", user.getId());
//                intent.putExtra("name", user.getFirstName()+user.getLastName());
//                startActivity(intent);
            }
        });

        lvUser.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }


}