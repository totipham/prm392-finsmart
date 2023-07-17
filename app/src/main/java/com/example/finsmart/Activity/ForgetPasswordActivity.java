package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button  verify;

    FirebaseAuth mAuth;

    FirebaseUser mUser;

    EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_forget_password);
        emailText = findViewById(R.id.edt_email_forgot);
        verify = findViewById(R.id.btn_verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email;
                email = emailText.getText().toString();
                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> signInMethods = task.getResult().getSignInMethods();
                                if (signInMethods != null && !signInMethods.isEmpty()) {
                                    // User with the given email exists
//                                    Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
//                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                // User with the given email does not exist
                                Toast.makeText(ForgetPasswordActivity.this, "Cannot find this account", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        findViewById(R.id.top_nav_back_button).setOnClickListener(v -> this.finish());
    }
}