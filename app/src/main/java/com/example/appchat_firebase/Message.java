package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Message extends AppCompatActivity {

    private RecyclerView rcvMessage;
    private MessageAdapter messageAdapter;
    private List<MessageOj> mListMessage;
    private EditText edtMessage;
    private Button btnSend;
    private TextView tvUserChat;
    private String idChat, idUserChat;
    private UserOj userChat;
    private DatabaseReference dbMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().hide();

        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        rcvMessage = findViewById(R.id.rcv_message);
        tvUserChat = findViewById(R.id.tv_name_contact);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);
        mListMessage = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(mListMessage);

        Intent intentChat = getIntent();
        if(intentChat == null){
            finish();
        }
        idChat = intentChat.getStringExtra("idChat");
        idUserChat = intentChat.getStringExtra("idUserChat");

        dbMessages = FirebaseDatabase.getInstance().getReference("chats").child(idChat).child("messages");
        dbMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListMessage.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessageOj messageOj = dataSnapshot.getValue(MessageOj.class);
                    mListMessage.add(messageOj);
                }
                mListMessage.sort(Comparator.comparing(MessageOj::getTime));
                rcvMessage.scrollToPosition(mListMessage.size()-1);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference("users").child(idUserChat);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userChat = snapshot.getValue(UserOj.class);
                tvUserChat.setText(userChat.getFirstName()+" "+userChat.getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rcvMessage.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }

            private void sendMessage() {
                String strMessage = edtMessage.getText().toString().trim();
                if(TextUtils.isEmpty(strMessage)){
                    return;
                }
                MessageOj message = new MessageOj(strMessage, Global.user.getId(), System.currentTimeMillis(), 0);
                String key = dbMessages.push().getKey();
                dbMessages.child(key).setValue(message);

                messageAdapter.notifyDataSetChanged();
                edtMessage.setText("");
            }
        });
    }
}