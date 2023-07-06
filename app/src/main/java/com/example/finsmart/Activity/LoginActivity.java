package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class LoginActivity extends AppCompatActivity {

    TextView forgetPassWord, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgetPassWord = (TextView)findViewById(R.id.txt_forget);
        forgetPassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassWordActivity.class);
                startActivity(intent);


            }
        });

        signUp = (TextView)findViewById(R.id.txt_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });
    }
}