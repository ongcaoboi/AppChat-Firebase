package com.example.appchat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySetting extends AppCompatActivity {

    private ImageView img_User;
    private TextView tv_RePassword , tv_Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        img_User = (ImageView) findViewById(R.id.img_User);
        tv_Logout = (TextView) findViewById(R.id.tv_Logout);
        tv_RePassword = (TextView) findViewById(R.id.tv_RePassword);

        tv_RePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}