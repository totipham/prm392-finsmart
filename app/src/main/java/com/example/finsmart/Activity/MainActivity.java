package com.example.finsmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

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

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//                case R.id.home:
//                    replaceFragment(homeFragment);
//                    break;
//                case R.id.dashboard:
//                    replaceFragment(dashboardFragment);
//                    break;
//                case R.id.wallet:
//                    replaceFragment(walletFragment);
//                    break;
//                case R.id.profile:
//                    replaceFragment(profileFragment);
//                    break;
//            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}