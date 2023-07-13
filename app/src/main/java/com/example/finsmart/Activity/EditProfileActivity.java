package com.example.finsmart.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finsmart.R;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtEmail;
    ImageView imvImage;
    static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imvImage = (ImageView) findViewById(R.id.imv_image);
        edtEmail = (EditText) findViewById(R.id.edt_email_editin4);

        //sửa khi hoàn thành code authen
        edtEmail.setText("admin@gmail.com");

    }

    public void onImageViewClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imvImage.setImageURI(imageUri);
        }
    }
}