package com.example.ezybiz2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.channels.Selector;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = null;
        if(currentUser==null){
            Intent intent = new Intent(SplashActivity.this, SelectorActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent main_intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(main_intent);
            finish();
        }

    }
}