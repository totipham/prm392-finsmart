package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button  verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        verify = findViewById(R.id.btn_verify);
        verify.setOnClickListener(v -> {
            Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.top_nav_back_button).setOnClickListener(v -> this.finish());
    }
}