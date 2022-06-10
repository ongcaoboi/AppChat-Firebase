package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat_firebase.services.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_login extends AppCompatActivity {

    private TextView btnRegister, toastMessage;
    private String registerOldEmail, registerOldPassword;
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private String email, password;
    private UserOj user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        if(Global.user != null ){
            Intent intent = new Intent(activity_login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        editEmail = (EditText) findViewById(R.id.inputEmail);
        editPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        toastMessage = (TextView) findViewById(R.id.messageToast);

        Intent intentRegister = getIntent();
        registerOldEmail = intentRegister.getStringExtra("email");
        registerOldPassword = intentRegister.getStringExtra("password");
        if(registerOldEmail != null && registerOldPassword != null){
            editEmail.setText(registerOldEmail);
            editPassword.setText(registerOldPassword);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage.setText("");
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();
                if(email.isEmpty() || email.equals("")){
                    toastMessage.setText("Email không được để trống");
                    return;
                }
                if(password.isEmpty() || password.equals("")){
                    toastMessage.setText("Mật khẩu không được để trống");
                    return;
                }
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                Query query = db.orderByChild("email").equalTo(email);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean isUser = false;
                        for (DataSnapshot data : snapshot.getChildren()){
                            UserOj userTmp = data.getValue(UserOj.class);
                            if(userTmp.getEmail().equals(email)){
                                user = userTmp;
                                isUser = true;
                                break;
                            }
                        }
                        if(!isUser){
                            toastMessage.setText("Không tìm thấy tài khoản");
                            return;
                        }
                        if(!user.getPassword().equals(password)){
                            toastMessage.setText("Mật khẩu không chính xác");
                            return;
                        }
                        Global.user = user;
                        Intent intent = new Intent(activity_login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnRegister = (TextView) findViewById(R.id.txtCreateAccount);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_login.this,activity_register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}