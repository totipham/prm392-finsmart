package com.example.finsmart.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProfileFragment extends Fragment {
    EditText edtEmail, edtName;
    ImageView imvImage;
    Button btnUpdate;
    ProgressBar progressBar, progressBarView;
    View loadingView;
    static final int REQUEST_CODE = 1;

    Uri imageURI;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Map<String, Object> user;

    String userName, userEmail, userAvatar, userDefaultWallet;
    Map config;

    public UpdateProfileFragment() {
    }

    public static UpdateProfileFragment newInstance() {
        return new UpdateProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserInformation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);

        user = new HashMap<>();

        progressBar = (ProgressBar) view.findViewById(R.id.progress_upload);
        progressBarView = (ProgressBar) view.findViewById(R.id.process_bar_loading);
        loadingView = (View) view.findViewById(R.id.view_loading);
        imvImage = (ImageView) view.findViewById(R.id.imv_image);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        btnUpdate = (Button) view.findViewById(R.id.btn_save);

        imvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.put("name", edtName.getText().toString());
                user.put("email", userEmail);
                user.put("avatar", userAvatar);
                user.put("defaultWallet", userDefaultWallet);

                db.collection("users").document(mUser.getUid()).set(user).addOnSuccessListener(aVoid -> {
                    //update shared preference in MainActivity
//                    ((MainActivity) getActivity()).saveUserInformation();
                    ((MainActivity)getActivity()).homeFragment.loadWelcomeName();
                    ((MainActivity)getActivity()).confirmTransferFragment.loadWelcomeName();
                    Toast.makeText(getContext(), "Update success", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    void bindData() {
        edtEmail.setText(userEmail);
        edtName.setText(userName);
        if (userAvatar != null && !userAvatar.isEmpty()) {
            Picasso.get().load(userAvatar.replace("http", "https")).into(imvImage);
        }

        progressBarView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void uploadToCloudinary() {
        MediaManager.get().upload(imageURI).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                //set progress bar visible here
                btnUpdate.setEnabled(false);
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                Double progress = (double) bytes / totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                progressBar.setProgress(progress.intValue() * 100);
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                user.put("avatar", resultData.get("url"));
                btnUpdate.setEnabled(true);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                Toast.makeText(getContext(), "Upload error", Toast.LENGTH_SHORT).show();
                btnUpdate.setEnabled(true);
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == FragmentActivity.RESULT_OK && data != null) {
            imageURI = data.getData();
            imvImage.setImageURI(imageURI);
            uploadToCloudinary();
        }
    }

    public void getUserInformation() {
        userName = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_name", "");
        userEmail = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_email", "");
        userAvatar = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_avatar", "");
        userDefaultWallet= this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_wallet", "");
        bindData();
    }
}