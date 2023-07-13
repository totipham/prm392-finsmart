package com.example.finsmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.finsmart.Fragment.DashboardFragment;
import com.example.finsmart.Fragment.HomeFragment;
import com.example.finsmart.Fragment.ProfileFragment;
import com.example.finsmart.Fragment.WalletFragment;
import com.example.finsmart.R;
import com.example.finsmart.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ProfileFragment profileFragment;
    HomeFragment homeFragment;
    WalletFragment walletFragment;
    DashboardFragment dashboardFragment;
    View topNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeFragment = new HomeFragment();
        walletFragment = new WalletFragment();
        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();

//        setContentView(R.layout.activity_main);
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