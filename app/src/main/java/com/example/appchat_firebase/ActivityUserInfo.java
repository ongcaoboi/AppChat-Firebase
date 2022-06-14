package com.example.appchat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserInfo extends AppCompatActivity {

    private ImageView img_UserInfo;
    private TextView tv_UserInfo, tv_Chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        tv_Chat = (TextView) findViewById(R.id.tv_Chat);
        tv_UserInfo = (TextView) findViewById(R.id.tv_NameUser);
        img_UserInfo = (ImageView) findViewById(R.id.img_UserInfo);

        tv_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_UserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}