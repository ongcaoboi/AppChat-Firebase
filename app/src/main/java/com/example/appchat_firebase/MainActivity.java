package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appchat_firebase.services.Global;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    ChatMain chatsMain;
    ContactMain contactMain;
    SettingsMain settingsMain;
    FragmentTransaction ft;

    TextView titleMain;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        chatsMain = new ChatMain();
        contactMain = new ContactMain();
        settingsMain = new SettingsMain();

        titleMain = (TextView) findViewById(R.id.txt_name_user);

        setFragment(chatsMain);
        titleMain.setText("Đoạn chat");

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
//                        setFragment(settingsMain);
                        String name = Global.user.getFirstName() + " " + Global.user.getLastName();
                        String email = Global.user.getEmail();
                        String sdt = Global.user.getSdt();
                        String gender = "male" ;
                        if(!Global.user.isGioiTinh()){
                            gender = "female";
                        }
                        Intent i = new Intent(MainActivity.this , ActivitySetting.class);
                        i.putExtra("name",name);
                        i.putExtra("email",email);
                        i.putExtra("sdt",sdt);
                        i.putExtra("gender",gender);
                        startActivity(i);
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
                    Intent i = new Intent(MainActivity.this , ActivitySetting.class);
                    startActivity(i);
                }
            }
        });
    }
    public void setFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.body_main, fragment);
        ft.commit();
    }
}