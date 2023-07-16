package com.example.finsmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.cloudinary.android.MediaManager;
import com.example.finsmart.Fragment.DashboardFragment;
import com.example.finsmart.Fragment.HomeFragment;
import com.example.finsmart.Fragment.ProfileFragment;
import com.example.finsmart.Fragment.UpdateProfileFragment;
import com.example.finsmart.Fragment.WalletFragment;
import com.example.finsmart.R;
import com.example.finsmart.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    WalletFragment walletFragment;
    DashboardFragment dashboardFragment;

    UpdateProfileFragment updateProfileFragment;
    View topNav;

    Map config;

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        config = new HashMap<>();
        config.put("cloud_name", "ddr0pf043");
        config.put("api_key", "814571977924379");
        config.put("api_secret", "p7WjgkOnh46EF1p9iN-Aa-6X0vY");
        config.put("secure", true);
        MediaManager.init(this, config);


        homeFragment = new HomeFragment(db);
        walletFragment = new WalletFragment();
        dashboardFragment = new DashboardFragment(db);
        profileFragment = new ProfileFragment();
//        updateProfileFragment = new UpdateProfileFragment(db, mAuth, mUser);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(homeFragment);
        topNav = findViewById(R.id.top_nav);
        topNav.setVisibility(View.GONE);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(homeFragment);
                topNav.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.dashboard) {
                binding.topNav.topHeaderTitle.setText("Transfer");
                replaceFragment(dashboardFragment);
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.wallet) {
                binding.topNav.topHeaderTitle.setText("Wallets");
                replaceFragment(walletFragment);
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.profile) {
                binding.topNav.topHeaderTitle.setText("Profile");
                replaceFragment(profileFragment);
                topNav.setVisibility(View.VISIBLE);
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}