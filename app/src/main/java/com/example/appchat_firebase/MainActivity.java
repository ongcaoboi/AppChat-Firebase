package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appchat_firebase.services.Global;
import com.example.appchat_firebase.services.LogNotificationService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private ChatMain chatsMain;
    private ContactMain contactMain;
    private FragmentTransaction ft;
    private TextView titleMain;
    private DatabaseReference dbUser;
    private ImageView imgUserMain;
    private Intent intentNotification;
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        chatsMain = new ChatMain();
        contactMain = new ContactMain();

        titleMain = (TextView) findViewById(R.id.txt_name_user);
        imgUserMain = (ImageView) findViewById(R.id.img_user_main);

        setFragment(chatsMain);
        titleMain.setText("Đoạn chat");

        if(Global.user == null){
            finish();
        }

        if(Global.user.isGioiTinh()){
            imgUserMain.setBackgroundResource(R.drawable.male);
        }else {
            imgUserMain.setBackgroundResource(R.drawable.female);
        }

        intentNotification = new Intent(this, LogNotificationService.class);
        startForegroundService(intentNotification);

        dbUser = FirebaseDatabase.getInstance().getReference("users").child(Global.user.getId()).child("trangThai");
        dbUser.setValue(true);
        dbUser.onDisconnect().setValue(false);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        titleMain.setText("Đoạn chat");
                        setFragment(chatsMain);
                        return true;
                    case R.id.page_2:
                        titleMain.setText("Danh bạ");
                        setFragment(contactMain);
                        return true;
                    case R.id.page_3:
                        openSetting();
                        return true;
                    default:
                        return false;
                }
            }
        });
        bottomNavigation.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                ListView listView;
                if(item.getItemId() == R.id.page_1){
                    listView = (ListView) findViewById(R.id.lv_chat_page);
                    listView.smoothScrollToPosition(0);
                }
                else if (item.getItemId() == R.id.page_2){
                    listView = (ListView) findViewById(R.id.lv_contact);
                    listView.smoothScrollToPosition(0);
                } else if (item.getItemId() == R.id.page_3){
                    openSetting();
                }
            }
        });
    }
    public void setFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.body_main, fragment);
        ft.commit();
    }
    public void openSetting(){
        String name = Global.user.getFirstName() + " " + Global.user.getLastName();
        String email = Global.user.getEmail();
        String sdt = Global.user.getSdt();
        String gender = "male" ;
        if(!Global.user.isGioiTinh()){
            gender = "female";
        }
        final Intent i = new Intent(this, ActivitySetting.class);
        i.putExtra("name",name);
        i.putExtra("email",email);
        i.putExtra("sdt",sdt);
        i.putExtra("gender",gender);
        startActivityForResult(i,REQUEST_CODE_EXAMPLE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_EXAMPLE) {
            if(resultCode == RESULT_OK){
                Intent intent = new Intent(this , activity_login.class);
                startActivity(intent);
                Intent intent_ = new Intent(this, LogNotificationService.class);
                Global.ACTION_FOREGROUND = false;
                startService(intent_);
                stopService(intentNotification);
                finish();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbUser.setValue(false);
    }
}