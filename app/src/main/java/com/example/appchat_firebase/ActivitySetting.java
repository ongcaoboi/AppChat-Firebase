package com.example.appchat_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivitySetting extends AppCompatActivity {

    private ImageView img_User;
    private TextView tv_RePassword , tv_Logout ,tv_Name , tv_Email , tv_PhoneNumber , tv_ReInfo;
    private Button btnQL;
    private String name , email,sdt ;
    private String gender;
    private DatabaseReference dbUser;
    private String oldFirstName , oldLastName , oldPhoneNumber;
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
        tv_ReInfo = (TextView) findViewById(R.id.tv_ReInfo);
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

        tv_ReInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(Gravity.CENTER);
            }
        });

        tv_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySetting.this , activity_login.class);
                startActivity(intent);
                Global.user = null;
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    private void openDialog(int gra){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_settings);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gra;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gra){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        EditText edtFirstname_ = dialog.findViewById(R.id.edtFirstName_);
        EditText edtLastname_ = dialog.findViewById(R.id.edtLastName_);
        EditText edtPhonenumber_ = dialog.findViewById(R.id.edtPhoneNumber_);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnConfirm = dialog.findViewById(R.id.btn_confirm);
        TextView toastMessage = dialog.findViewById(R.id.messageToast);


        edtFirstname_.setText(Global.user.getFirstName());
        edtLastname_.setText(Global.user.getLastName());
        edtPhonenumber_.setText(Global.user.getSdt());

        oldFirstName = Global.user.getFirstName();
        oldLastName = Global.user.getLastName();
        oldPhoneNumber = Global.user.getSdt();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(edtFirstname_.getText().toString().equals(oldFirstName) || edtLastname_.getText().toString().equals(oldLastName) || edtPhonenumber_.getText().toString().equals(oldPhoneNumber) ){
//                    Toast.makeText(getApplicationContext(), "Hãy thay đổi thông tin của bạn ! ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(edtFirstname_.getText().toString().equals("") || edtLastname_.getText().toString().equals("") || edtPhonenumber_.getText().toString().equals("") ){
                    Toast.makeText(getApplicationContext(), "Hãy thay đổi thông tin của bạn ! ", Toast.LENGTH_SHORT).show();
                    return;
                }
                dbUser = FirebaseDatabase.getInstance().getReference("users").child(Global.user.getId());
                UserOj user1 = Global.user;
                user1.setFirstName(edtFirstname_.getText().toString());
                user1.setLastName(edtLastname_.getText().toString());
                user1.setSdt(edtPhonenumber_.getText().toString());
                dbUser.setValue(user1);

                Global.user = user1;

                String name = Global.user.getFirstName()+ " " + Global.user.getLastName();
                tv_Name.setText(name);
                tv_PhoneNumber.setText(Global.user.getSdt());

                dialog.dismiss();

            }
        });

        dialog.show();


    }
}