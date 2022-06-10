package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_register extends AppCompatActivity {

    private TextView btnLogin, toastMessage;
    private EditText editEmail, editPhone, editFirstName, editLastName, editGender, editPassword, editRePassword;
    private RadioGroup radioGender;
    private Button btnRegister;
    boolean emailNotUnique = false;
    String key = "";

    String email;
    String phone;
    String firstName;
    String lastName;
    String password;
    String rePassword;
    Boolean gender = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        btnLogin = findViewById(R.id.txtLogin);
        btnRegister = findViewById(R.id.btnLogin);
        editEmail = (EditText) findViewById(R.id.inputEmail);
        editPhone = (EditText) findViewById(R.id.inputSdt);
        editFirstName = (EditText) findViewById(R.id.inputFirstName);
        editLastName = (EditText) findViewById(R.id.inputLastName);
        editPassword = (EditText) findViewById(R.id.inputPassword);
        editRePassword = (EditText) findViewById(R.id.inputRePassword);
        radioGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        toastMessage = (TextView) findViewById(R.id.messageToast);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastMessage.setText("");
                email = editEmail.getText().toString().trim();
                phone = editPhone.getText().toString().trim();
                firstName = editFirstName.getText().toString().trim();
                lastName = editLastName.getText().toString().trim();
                password = editPassword.getText().toString().trim();
                rePassword = editRePassword.getText().toString().trim();
                gender = null;
                switch (radioGender.getCheckedRadioButtonId()){
                    case R.id.radioMale:
                        gender = true;
                        break;
                    case R.id.radioFemale:
                        gender = false;
                        break;
                }
                if(email.isEmpty() || email.equals("")){
                    toastMessage.setText("Email không được để trống");
                    return;
                }
                if(phone.isEmpty() || phone.equals("")){
                    toastMessage.setText("Số điện thoại không được để trống");
                    return;
                }
                if(firstName.isEmpty() || firstName.equals("")){
                    toastMessage.setText("Họ không được để trống");
                    return;
                }
                if(lastName.isEmpty() || lastName.equals("")){
                    toastMessage.setText("Tên không được để trống");
                    return;
                }
                if(password.isEmpty() || password.equals("")){
                    toastMessage.setText("Mật khẩu không được để trống");
                    return;
                }
                if(!password.equals(rePassword) || rePassword.isEmpty()){
                    toastMessage.setText("Mật khẩu nhập lại không đúng");
                    return;
                }
                if(gender == null){
                    toastMessage.setText("Hãy chọn giới tính");
                    return;
                }
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("users");
                if(key.equals("")) {
                    key = db.push().getKey();
                }
                Query query = db.orderByChild("email").equalTo(email);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean tmp = false;
                        for (DataSnapshot data : snapshot.getChildren()){
                            UserOj userTmp = data.getValue(UserOj.class);
                            if(userTmp.getEmail().equals(email)){
                                tmp = true;
                                break;
                            }
                        }
                        emailNotUnique = tmp;
                        if(emailNotUnique){
                            toastMessage.setText("Email đã được sử dụng");
                            return;
                        }
                        UserOj user = new UserOj(key, email, firstName, lastName, phone, password, gender, false);
                        db.child(key).setValue(user);
                        Intent intent = new Intent(activity_register.this, activity_login.class);
                        intent.putExtra("email", user.getEmail());
                        intent.putExtra("password", user.getPassword());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_register.this,activity_login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}