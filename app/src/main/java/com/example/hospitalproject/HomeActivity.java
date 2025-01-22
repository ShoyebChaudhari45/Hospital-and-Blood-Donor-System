package com.example.hospitalproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    Boolean doubletap = false;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        viewPager2 = findViewById(R.id.viewpager);
        setTitle("Red Town");

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_homeid1) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.navigation_request1) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.navigation_contribute1) {
                    viewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        // Set up ViewPager page change callback
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_homeid1).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_request1).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navigation_contribute1).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.profile_menu1) {
            Toast.makeText(this, "My profile", Toast.LENGTH_SHORT).show();
            Intent profileIntent = new Intent(HomeActivity.this, MyProfileActivity.class);
            startActivity(profileIntent);
            return true;
        } else if (itemId == R.id.about_menu1) {
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
            Intent aboutIntent = new Intent(HomeActivity.this, AboutUs.class);
            startActivity(aboutIntent);
            return true;
        } else if (itemId == R.id.logout_menu1) {
            Toast.makeText(this, "LogOut", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
            ad.setTitle("Logout");
            ad.setMessage("Are you sure you want to logout?");
            ad.setPositiveButton("Cancel", (dialogInterface, which) -> dialogInterface.cancel());
            ad.setNegativeButton("Logout", (dialogInterface, which) -> {
                Intent logoutIntent = new Intent(HomeActivity.this, LoginPageActivity.class);
                editor.putBoolean("isLogin", false).apply();
                startActivity(logoutIntent);
                finish();
            }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubletap) {
            super.onBackPressed();
        } else {
            Toast.makeText(HomeActivity.this, "Press Again To Exit", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubletap = false;
                }
            }, 3000);
        }
    }
}