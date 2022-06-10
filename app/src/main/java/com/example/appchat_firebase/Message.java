package com.example.appchat_firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appchat_firebase.services.Global;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    private RecyclerView rcvMessage;
    private MessageAdapter messageAdapter;
    private List<MessageOj> mListMessage;
    private EditText edtMessage;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().hide();

        edtMessage = findViewById(R.id.edt_message);
        btnSend = findViewById(R.id.btn_send);
        rcvMessage = findViewById(R.id.rcv_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvMessage.setLayoutManager(linearLayoutManager);

        mListMessage = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(mListMessage);

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

                mListMessage.add(new MessageOj(strMessage, Global.user.getId(), System.currentTimeMillis(), 0));
                messageAdapter.notifyDataSetChanged();
                rcvMessage.scrollToPosition(mListMessage.size()-1);

                edtMessage.setText("");
            }
        });
    }
}