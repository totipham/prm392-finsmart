package com.example.finsmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.example.finsmart.Fragment.AddNewWalletFragment;
import com.example.finsmart.Fragment.DashboardFragment;
import com.example.finsmart.Fragment.HomeFragment;
import com.example.finsmart.Fragment.ProfileFragment;
import com.example.finsmart.Fragment.ProfilePreferenceFragment;
import com.example.finsmart.Fragment.WalletFragment;
import com.example.finsmart.R;
import com.example.finsmart.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    String currentFragmentTag;
    HomeFragment homeFragment;
    WalletFragment walletFragment;
    ProfileFragment profileFragment;
    DashboardFragment dashboardFragment;
    public ProfilePreferenceFragment profilePreferenceFragment;
    public AddNewWalletFragment addNewWalletFragment;
    View topNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentFragmentTag="home";
        addFragment();
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(homeFragment,"home","");
        topNav = findViewById(R.id.top_nav);
        topNav.setVisibility(View.GONE);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(homeFragment,"home","");
                topNav.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.dashboard) {
                replaceFragment(dashboardFragment,"transfer","Transfer");
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.wallet) {
                replaceFragment(walletFragment,"wallet","Wallets");
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(profileFragment,"profile","Profile");
                topNav.setVisibility(View.VISIBLE);
            }
            return true;
        });

        binding.topNav.topNavBackButton.setOnClickListener(v -> {
            if(currentFragmentTag.matches("home||profile||wallet||transfer")){
                binding.bottomNavigationView.findViewById(R.id.home).performClick();
            }else{
                switch (currentFragmentTag){
                    case "profilePreference":
                        replaceFragment(profileFragment,"profile","Profile");
                        break;
                    case "editInformation":
//                            replaceFragment(,"editInfo","Edit Information");
                        break;
                    case "addWallet":
                        replaceFragment(walletFragment,"wallet","Wallets");
                        break;
                    case "amountTransfer":
                        replaceFragment(dashboardFragment,"transfer","Transfer");
                        break;
                    case "confirmTransfer":
//                            replaceFragment(,"amountTransfer","Transfer");
                        break;
                    case "transactionHistory":
                        binding.bottomNavigationView.findViewById(R.id.home).performClick();
                        break;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment, String tag, String topNavText) {
        currentFragmentTag = tag;
        binding.topNav.topHeaderTitle.setText(topNavText);
        for(int i = 0; i < fragmentManager.getFragments().size();i++){
            fragmentManager.beginTransaction().hide(fragmentManager.getFragments().get(i)).commitNow();
        }
        fragmentManager.beginTransaction().show(fragment).commitNow();
    }

    public void addFragment() {
        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        walletFragment = new WalletFragment();
        dashboardFragment = new DashboardFragment();
        profileFragment = new ProfileFragment();
        profilePreferenceFragment = new ProfilePreferenceFragment();
        addNewWalletFragment = new AddNewWalletFragment();

        fragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment,"home").commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, walletFragment,"wallet").commit();
        fragmentManager.beginTransaction().hide(walletFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, dashboardFragment,"transfer").commit();
        fragmentManager.beginTransaction().hide(dashboardFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, profileFragment,"profile").commit();
        fragmentManager.beginTransaction().hide(profileFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, profilePreferenceFragment,"profilePreference").commit();
        fragmentManager.beginTransaction().hide(profilePreferenceFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, addNewWalletFragment,"addWallet").commit();
        fragmentManager.beginTransaction().hide(addNewWalletFragment).commitNow();
    }
}