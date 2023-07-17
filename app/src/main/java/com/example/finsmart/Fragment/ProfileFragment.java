package com.example.finsmart.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String userName, userEmail, userAvatar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile, container, false);
        email = mView.findViewById(R.id.tv_email);
        name = mView.findViewById(R.id.tv_profile_name);
        profileImage = mView.findViewById(R.id.img_my_avatar);
        user_preference = mView.findViewById(R.id.user_preference);
        user_logout = mView.findViewById(R.id.user_logout);
        user_preference.setOnClickListener(v -> ((MainActivity) getActivity()).replaceFragment(
                ((MainActivity) getActivity()).profilePreferenceFragment,
                "profilePreference", "Preferences"
        ));
        user_logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        return mView;
    }

    public void getUserInformation() {
        userName = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_name", "");
        userEmail = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_email", "");
        userAvatar = this.getActivity().getSharedPreferences("FinSmartPref", Context.MODE_PRIVATE).getString("user_avatar", "");

        email.setText(userEmail);
        name.setText(userName);

        if (userAvatar != null) {
            Picasso.get().load(userAvatar.replace("http", "https")).into(profileImage);
        }
    }

}