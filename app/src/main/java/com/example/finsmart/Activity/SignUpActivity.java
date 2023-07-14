package com.example.finsmart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextView signIn;

    Button signup;
    FirebaseAuth mAuth;

    FirebaseFirestore db;
    EditText full_name, email, pasword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        signIn = (TextView)findViewById(R.id.txt_login);
        full_name = (EditText)findViewById(R.id.edt_fullname_signup);
        email = (EditText)findViewById(R.id.edt_email_signup);
        pasword = (EditText)findViewById(R.id.edt_password_signup);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signup = (Button)findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(full_name.getText());
                String e = String.valueOf(email.getText());
                String pass = String.valueOf(pasword.getText());
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(e)  || TextUtils.isEmpty(pass) ){
                    Toast.makeText(SignUpActivity.this, "Please enter everything", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(e, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name.toString());
                                    db.collection("users").document(currentUser.getUid()).set(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Signup", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}