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
            if(item.getItemId() == R.id.home){
                replaceFragment(homeFragment);
                topNav.setVisibility(View.GONE);
            }else if(item.getItemId() == R.id.dashboard){
                replaceFragment(dashboardFragment);
                topNav.setVisibility(View.VISIBLE);
            }else if(item.getItemId() == R.id.wallet){
                replaceFragment(walletFragment);
                topNav.setVisibility(View.VISIBLE);
            }else if(item.getItemId() == R.id.profile){
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