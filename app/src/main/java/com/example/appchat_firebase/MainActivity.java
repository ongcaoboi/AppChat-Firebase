package com.example.appchat_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    ChatMain chatsMain;
    ContactMain contactMain;
    SettingsMain settingsMain;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        chatsMain = new ChatMain();
        contactMain = new ContactMain();
        settingsMain = new SettingsMain();

        setFragment(chatsMain);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_1:
                        setFragment(chatsMain);
                        return true;
                    case R.id.page_2:
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
                switch (item.getItemId()) {
                    case R.id.page_1:
                        ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_chat_main);
                        scrollView.fullScroll(ScrollView.FOCUS_UP);
                        break;
                    case R.id.page_2:
                        Toast.makeText(MainActivity.this, "Page_2", Toast.LENGTH_SHORT).show();
                        break;
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