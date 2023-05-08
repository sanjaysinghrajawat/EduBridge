package com.example.edubridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("loginSharedPreferences", MODE_PRIVATE);
                Boolean check = pref.getBoolean("flag_login", false);

                Intent nextActivity;
                if(check) // user logged in
                {
                    nextActivity = new Intent(SplashActivity.this, MainActivity.class);
                }
                else // either first time login or logout
                {
                    nextActivity = new Intent(SplashActivity.this, login_using_number.class);
                }
                startActivity(nextActivity);
                finish();
            }
        },4000);
    }
}