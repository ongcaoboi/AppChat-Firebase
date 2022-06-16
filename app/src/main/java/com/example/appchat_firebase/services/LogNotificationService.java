package com.example.appchat_firebase.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.appchat_firebase.MessageOj;
import com.example.appchat_firebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogNotificationService extends Service {
    private Handler mHandler = new Handler();
    private int index = 0;
    private int size = 0;
    private List<String> listIdUserTmp = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Global.ACTION_FOREGROUND){
                    if(Global.chats != null && Global.user != null){
                        if(Global.chats.size() != size){
                            index = 0;
                            size = Global.chats.size();
                        }
                        if(index > 0) {
                            mHandler.postDelayed(this, 1000);
                            return;
                        };
                        index = 1;
                        listIdUserTmp.clear();
                        for(ChatMainTmp chat : Global.chats) {
                            boolean tonTai = false;
                            for (String id : listIdUserTmp){
                                if(id.equals(chat.getUser().getId())){
                                    tonTai = true;
                                    break;
                                }
                            }
                            if(!tonTai){
                                listIdUserTmp.add(chat.getUser().getId());
                                DatabaseReference dbChat = FirebaseDatabase.getInstance().getReference("chats/"+chat.getChatInfo().getIdChat()+"/messages");
                                dbChat.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(chat.getChatInfo().getIdChat().equals(Global.idChatOnOpen)) return;
                                        List<MessageOj> list = new ArrayList<>();
                                        for(DataSnapshot chat : snapshot.getChildren()){
                                            MessageOj messageOj = chat.getValue(MessageOj.class);
                                            list.add(messageOj);
                                        }
                                        list.sort(Comparator.comparing(MessageOj::getTime).reversed());
                                        if(!list.isEmpty() && Global.user != null) {
                                            if (list.get(0).getUserId().equals(Global.user.getId())) return;
                                            sendNotificationMessage(chat.getUser().getFirstName() + " " + chat.getUser().getLastName(), list.get(0).getMsg());
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    }
                }else {
                    stopForeground(true);
                    stopSelfResult(startId);
                }
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
        sendNotificationMessage("AppChat Firebase", "Running...");
        return START_NOT_STICKY;
    }
    private void sendNotificationMessage(String name, String message)  {

        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_send_message)
            .setContentTitle(name)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_PROMO) // Promotion.
            .build();

        startForeground(1, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
