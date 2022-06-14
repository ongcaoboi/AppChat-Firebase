package com.example.appchat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appchat_firebase.services.Global;

public class ActivitySetting extends AppCompatActivity {

    private ImageView img_User;
    private TextView tv_RePassword , tv_Logout ,tv_Name , tv_Email , tv_PhoneNumber;
    private Button btnQL;
    private String name , email,sdt ;
    private String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        tv_Name = (TextView) findViewById(R.id.tv_Name);
        tv_Email = (TextView) findViewById(R.id.tv_Email);
        tv_PhoneNumber = (TextView) findViewById(R.id.tv_PhoneNumber);
        img_User = (ImageView) findViewById(R.id.img_User);

        tv_Logout = (TextView) findViewById(R.id.tv_Logout);
        tv_RePassword = (TextView) findViewById(R.id.tv_RePassword);

        Intent intentMain = getIntent();
        name  = intentMain.getStringExtra("name");
        email = intentMain.getStringExtra("email");
        sdt = intentMain.getStringExtra("sdt");
        gender = intentMain.getStringExtra("gender");
        if(gender.equals("male")){
            img_User.setBackgroundResource(R.drawable.male);
        } else {
            img_User.setBackgroundResource(R.drawable.female);
        }
        if(name != null && email != null && sdt != null){
            tv_Name.setText(name);
            tv_PhoneNumber.setText(sdt);
            tv_Email.setText(email);
        }



        btnQL = (Button) findViewById(R.id.btnQL);
        btnQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_RePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tv_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySetting.this , activity_login.class);
                startActivity(intent);
                Global.user = null ;

                finish();
            }
        });
    }
}