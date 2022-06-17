package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat_firebase.services.ChatProcess;
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
    private List<ChatProcess> mListMessageProcess;
    private EditText edtMessage;
    private Button btnSend, btnBack;
    private TextView tvUserChat;
    private String idChat, idUserChat ;
    private UserOj userChat;
    private DatabaseReference dbMessages;
    private ImageView dotStatus, imgUserChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().hide();

        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        btnBack = findViewById(R.id.btn_back);
        rcvMessage = findViewById(R.id.rcv_message);
        tvUserChat = findViewById(R.id.tv_name_contact);
        dotStatus = findViewById(R.id.dot_status);
        imgUserChat = (ImageView) findViewById(R.id.image_avatar_contact);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);
        mListMessage = new ArrayList<>();
        mListMessageProcess = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(mListMessage);

        Intent intentChat = getIntent();
        if(intentChat == null){
            finish();
        }
        idChat = intentChat.getStringExtra("idChat");
        idUserChat = intentChat.getStringExtra("idUserChat");

        Global.idChatOnOpen = idChat;

        dbMessages = FirebaseDatabase.getInstance().getReference("chats").child(idChat).child("messages");
        dbMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mListMessage.clear();
                mListMessageProcess.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessageOj messageOj = dataSnapshot.getValue(MessageOj.class);
                    String key = dataSnapshot.getKey();
                    mListMessage.add(messageOj);
                    mListMessageProcess.add(new ChatProcess(key, messageOj.getUserId(), messageOj));
                }
                mListMessage.sort(Comparator.comparing(MessageOj::getTime));
                mListMessageProcess.sort(Comparator.comparing(ChatProcess::getTime).reversed());
                rcvMessage.scrollToPosition(mListMessage.size()-1);
                if(!mListMessageProcess.isEmpty()){
                    ChatProcess chatProcess = mListMessageProcess.get(0);
                    if(idChat.equals(Global.idChatOnOpen) && !chatProcess.getIdUser().equals(Global.user.getId())){
                        System.out.println(chatProcess.getMessageNew().getMsg());
                        dbMessages.child(chatProcess.getIdChat()).child("status").setValue(1);
                    }
                }
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
                if(userChat.isTrangThai()){
                    dotStatus.setBackgroundResource(R.drawable.dot_online);
                }else{
                    dotStatus.setBackgroundResource(R.drawable.dot_offline);
                }

                if(userChat.isGioiTinh()){
                    imgUserChat.setBackgroundResource(R.drawable.male);
                }else {
                    imgUserChat.setBackgroundResource(R.drawable.female);
                }
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onKeyboard();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Global.idChatOnOpen = null;
    }
    private void onKeyboard(){
        final View activityRootView = findViewById(R.id.messageRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25*activityRootView.getRootView().getHeight()){
                    if(mListMessage.size() > 0){
                        rcvMessage.scrollToPosition(mListMessage.size() -1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}