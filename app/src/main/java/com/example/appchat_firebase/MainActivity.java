package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appchat_firebase.services.FireBaseService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        UserOj testFireBase = new UserOj("tuanAnh2@gmail.com", "Tuan", "Anh", "0987654321", true, true);
        FireBaseService fireBaseService = new FireBaseService(testFireBase);

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
                        setFragment(settingsMain);
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