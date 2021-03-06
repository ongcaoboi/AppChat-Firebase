package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat_firebase.services.ChatTmp;
import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivityUserInfo extends AppCompatActivity {

    private ImageView img_UserInfo, dotStatus;
    private TextView tv_UserInfo, tv_Chat , tv_Phonenumber;
    private Button btnQL;
    private String idUserChat, nameUserChat, sdt , idChat = "";
    DatabaseReference dbContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().hide();

        tv_Chat = (TextView) findViewById(R.id.tv_Chat);
        tv_UserInfo = (TextView) findViewById(R.id.tv_NameUser);
        tv_Phonenumber = (TextView) findViewById(R.id.tv_PhoneNumber);
        img_UserInfo = (ImageView) findViewById(R.id.img_UserInfo);
        dotStatus = (ImageView) findViewById(R.id.dot_status);

        dbContact  = FirebaseDatabase.getInstance().getReference("chats");
        dbContact.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot chat : snapshot.getChildren()){
                    String key = chat.getKey();
                    ChatTmp chatTmp = chat.getValue(ChatTmp.class);
                    if(chatTmp.getUser_1().equals(Global.user.getId())){
                        if(chatTmp.getUser_2().equals(idUserChat)){
                            idChat = key;
                        }
                    }else if(chatTmp.getUser_2().equals(Global.user.getId())){
                        if(chatTmp.getUser_1().equals(idUserChat)){
                            idChat = key;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intentUser = getIntent();
        idUserChat = intentUser.getStringExtra("id");
        if(idUserChat == null){
            finish();
        }
        nameUserChat = intentUser.getStringExtra("name");
        sdt = intentUser.getStringExtra("sdt");

        tv_UserInfo.setText(nameUserChat);
        tv_Phonenumber.setText(sdt);

        String gender = intentUser.getStringExtra("gender");
        if(gender.equals("male")){
            img_UserInfo.setBackgroundResource(R.drawable.male);
        }else{
            img_UserInfo.setBackgroundResource(R.drawable.female);
        }

        String status = intentUser.getStringExtra("status");
        if(gender.equals("online")){
            dotStatus.setBackgroundResource(R.drawable.dot_online);
        }else{
            dotStatus.setBackgroundResource(R.drawable.dot_offline);
        }

        tv_Phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = Alert();
                dialog.show();

//                String sdt = tv_Phonenumber.getText().toString();
//                Intent i = new Intent(Intent.ACTION_DIAL);
//                i.setData(Uri.parse("tel: " + sdt));
//                startActivity(i);
            }
        });
        btnQL = (Button) findViewById(R.id.btnQL);
        btnQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!idChat.equals("")){
                    Intent intent = new Intent(getApplicationContext(), Message.class);
                    intent.putExtra("idChat", idChat);
                    intent.putExtra("idUserChat", idUserChat);
                    startActivity(intent);
                    finish();
                }else {
                    openSendDialog(Gravity.CENTER);
                }
            }
        });
    }
    private void openSendDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_send_message);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        EditText editContent = dialog.findViewById(R.id.input_content);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSend = dialog.findViewById(R.id.btn_send);

        tvTitle.setText(tvTitle.getText().toString()+" "+nameUserChat);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editContent.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "H??y nh???p n???i dung tin nh???n!", Toast.LENGTH_SHORT).show();
                    return;
                }
                idChat = dbContact.push().getKey();
                MessageOj message = new MessageOj(editContent.getText().toString(), Global.user.getId(), System.currentTimeMillis(), 0);
                ChatTmp chatTmp = new ChatTmp(Global.user.getId(), idUserChat, "");
                dbContact.child(idChat).setValue(chatTmp);
                String key = dbContact.child(idChat).child("messages").push().getKey();
                System.out.println(key);
                dbContact.child(idChat).child("messages").child(key).setValue(message);

                Intent intent = new Intent(getApplicationContext(), Message.class);
                intent.putExtra("idChat", idChat);
                intent.putExtra("idUserChat", idUserChat);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    private AlertDialog Alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Quay s???");
        builder.setMessage("B???n c?? mu???n th???c hi???n quay s??? ?");
        builder.setCancelable(false);

        builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sdt = tv_Phonenumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: " + sdt));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        return dialog;
    }
}