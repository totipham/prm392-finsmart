package com.example.finsmart.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        findViewById(R.id.top_nav_back_button).setOnClickListener(v -> this.finish());
    }
}