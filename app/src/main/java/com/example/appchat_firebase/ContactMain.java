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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                Intent intent = new Intent(getContext(), Message.class);
                UserOj user = arrayUser.get(i);
                intent.putExtra("id", user.getId());
                intent.putExtra("name", user.getFirstName()+user.getLastName());
                startActivity(intent);
            }
        });

        lvUser.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }


}