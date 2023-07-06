package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button  verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        verify = (Button) findViewById(R.id.btn_verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                startActivity(intent);


            }
        });
    }
}