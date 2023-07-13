package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class LoginActivity extends AppCompatActivity {

    TextView forgetPassWord, signUp;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgetPassWord = findViewById(R.id.txt_forget);
        forgetPassWord.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
            startActivity(intent);
        });

        signUp = findViewById(R.id.txt_signup);
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        edtEmail = findViewById(R.id.edt_email_signin);
        edtPassword = findViewById(R.id.edt_password_signin);
        btnLogin = findViewById(R.id.btn_signin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        Log.d("Debug",email + pass);
        if(email.isEmpty()){
            edtEmail.setError("Please enter Email");
        }
        if(pass.isEmpty()){
            edtPassword.setError("Please enter Password");
        }
        if (!email.equals("admin@gmail.com")  || !pass.equals("123")){
            Toast.makeText(LoginActivity.this, "Please check Email/ Password", Toast.LENGTH_SHORT).show();
        }

        if (email.equals("admin@gmail.com") && pass.equals("123")){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}