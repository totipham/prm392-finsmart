package com.example.finsmart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

import com.cloudinary.android.MediaManager;
import com.example.finsmart.Fragment.AddNewWalletFragment;
import com.example.finsmart.Fragment.AmountTransferFragment;
import com.example.finsmart.Fragment.DashboardFragment;
import com.example.finsmart.Fragment.EditWalletFragment;
import com.example.finsmart.Fragment.HomeFragment;
import com.example.finsmart.Fragment.ProfileFragment;
import com.example.finsmart.Fragment.TransferFragment;
import com.example.finsmart.Fragment.UpdateProfileFragment;
import com.example.finsmart.Fragment.ProfilePreferenceFragment;
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
    FragmentManager fragmentManager;
    String currentFragmentTag;
    HomeFragment homeFragment;
    WalletFragment walletFragment;
    ProfileFragment profileFragment;
    DashboardFragment dashboardFragment;
    public ProfilePreferenceFragment profilePreferenceFragment;
    public AddNewWalletFragment addNewWalletFragment;
    public UpdateProfileFragment updateProfileFragment;
    public TransferFragment transferFragment;
    public AmountTransferFragment amountTransferFragment;
    public TransferFragment confirmTransferFragment;
    public EditWalletFragment editWalletFragment;
    View topNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentFragmentTag = "home";
        addFragment();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(homeFragment, "home", "");
        topNav = findViewById(R.id.top_nav);
        topNav.setVisibility(View.GONE);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(homeFragment, "home", "");
                topNav.setVisibility(View.GONE);
            } else if (item.getItemId() == R.id.dashboard) {
                replaceFragment(dashboardFragment, "transfer", "Transfer");
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.wallet) {
                replaceFragment(walletFragment, "wallet", "Wallets");
                topNav.setVisibility(View.VISIBLE);
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(profileFragment, "profile", "Profile");
                topNav.setVisibility(View.VISIBLE);
            }
            return true;
        });

        binding.topNav.topNavBackButton.setOnClickListener(v -> {
            if (currentFragmentTag.matches("home||profile||wallet||transfer")) {
                binding.bottomNavigationView.findViewById(R.id.home).performClick();
            } else {
                switch (currentFragmentTag) {
                    case "profilePreference":
                        replaceFragment(profileFragment, "profile", "Profile");
                        break;
                    case "editInformation":
                        replaceFragment(profilePreferenceFragment,"profilePreference","Preferences");
                        break;
                    case "addWallet":
                    case "editWallet":
                        replaceFragment(walletFragment, "wallet", "Wallets");
                        break;
                    case "amountTransfer":
                        replaceFragment(dashboardFragment, "transfer", "Transfer");
                        break;
                    case "confirmTransfer":
                        replaceFragment(amountTransferFragment,"amountTransfer","Transfer");
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
        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {
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
        updateProfileFragment = new UpdateProfileFragment();
        transferFragment = new TransferFragment();
        amountTransferFragment = new AmountTransferFragment();
        confirmTransferFragment = new TransferFragment();
        editWalletFragment = new EditWalletFragment();

        fragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment, "home").commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, walletFragment, "wallet").commit();
        fragmentManager.beginTransaction().hide(walletFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, dashboardFragment, "transfer").commit();
        fragmentManager.beginTransaction().hide(dashboardFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, profileFragment, "profile").commit();
        fragmentManager.beginTransaction().hide(profileFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, profilePreferenceFragment, "profilePreference").commit();
        fragmentManager.beginTransaction().hide(profilePreferenceFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, addNewWalletFragment, "addWallet").commit();
        fragmentManager.beginTransaction().hide(addNewWalletFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, transferFragment, "1").commit();
        fragmentManager.beginTransaction().hide(transferFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, amountTransferFragment, "amountTransfer").commit();
        fragmentManager.beginTransaction().hide(amountTransferFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, confirmTransferFragment, "confirmTransfer").commit();
        fragmentManager.beginTransaction().hide(confirmTransferFragment).commitNow();
//        fragmentManager.beginTransaction().add(R.id.frameLayout, updateProfileFragment, "editInformation").commit();
//        fragmentManager.beginTransaction().hide(updateProfileFragment).commitNow();
        fragmentManager.beginTransaction().add(R.id.frameLayout, editWalletFragment, "editWallet").commit();
        fragmentManager.beginTransaction().hide(editWalletFragment).commitNow();
    }
}