package com.example.finsmart.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finsmart.Activity.LoginActivity;
import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Activity.SignUpActivity;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private View mView;
    private LinearLayout user_preference;
    private LinearLayout user_logout;

    private TextView email, name;
    private ImageView profileImage;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    FirebaseFirestore db;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        user_preference = mView.findViewById(R.id.user_preference);
        user_logout = mView.findViewById(R.id.user_logout);
        user_preference.setOnClickListener(v -> ((MainActivity)getActivity()).replaceFragment(
                ((MainActivity) getActivity()).profilePreferenceFragment,
                "profilePreference","Preferences"
        ));
        user_logout.setOnClickListener(v -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
        });

        //get email
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            email = mView.findViewById(R.id.tv_email);
            name = mView.findViewById(R.id.tv_profile_name);
            profileImage = mView.findViewById(R.id.img_my_avatar);

            email.setText(mUser.getEmail());
            DocumentReference docRef = db.collection("users").document(mUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        name.setText(document.getString("name"));
                        Picasso.get().load(document.getString("avatar").replace("http", "https")).into(profileImage);
                    }
                }
            });
        }
        // Inflate the layout for this fragment
        return mView;
    }
}