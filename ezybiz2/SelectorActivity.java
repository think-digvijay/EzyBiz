package com.example.ezybiz2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class SelectorActivity extends AppCompatActivity {
    private Button btn;
    private Button btn2;
    private FrameLayout parentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        btn = findViewById(R.id.customer);
        btn2 = findViewById(R.id.manufacturer);
        parentFrameLayout = findViewById(R.id.reg_frameLayout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                ;
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this, RegisterActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
