package com.example.rentx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.rentx.screens.HomeActivity;
import com.example.rentx.screens.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Splash Screen will wait for 2 seconds and then Login Activity will be launched.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    // if user is already logged in he'll be redirected to home screen
                    // he donot need to log in again
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 2000);
    }
}