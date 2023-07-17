package com.example.finsmart.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditWalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditWalletFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    TextView wallet;
    EditText edtWalletName, edtWalletAmount;
    SwitchCompat isDefault;

    Wallet selectedWallet;
    String defaultWalletId;

    public EditWalletFragment() {
        // Required empty public constructor
    }

    public static EditWalletFragment newInstance(String param1, String param2) {
        return new EditWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_wallet, container, false);
        wallet = (TextView) view.findViewById(R.id.txt_wallet);
        edtWalletName = (EditText) view.findViewById(R.id.edt_name);
        edtWalletAmount = (EditText) view.findViewById(R.id.edt_amount);
        isDefault = (SwitchCompat) view.findViewById(R.id.isDefault_swt);


        edtWalletName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    wallet.setText("Name here");
                } else {
                    wallet.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button confirm = (Button) view.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update wallet to firestore
                Map<String, Object> wallet = new HashMap<>();
                wallet.put("name", edtWalletName.getText().toString());
                wallet.put("balance", Double.parseDouble(edtWalletAmount.getText().toString()));

                db.collection("wallets").document(selectedWallet.getWalletId())
                        .update(wallet);

                //set default wallet to user
                if (isDefault.isChecked()) {
                    Toast.makeText(getActivity(), "Default wallet set", Toast.LENGTH_SHORT).show();
                    Map<String, Object> user = new HashMap<>();
                    user.put("defaultWallet", selectedWallet.getWalletId());
                    db.collection("users").document(mUser.getUid())
                            .update(user);
                }

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void setWallet(Wallet selectedWallet) {
        this.selectedWallet = selectedWallet;

        wallet.setText(selectedWallet.getName());
        edtWalletName.setText(selectedWallet.getName());
        edtWalletAmount.setText(String.valueOf(selectedWallet.getBalance()));

        //check db for default wallet
        db.collection("users").document(mUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        defaultWalletId = task.getResult().getString("defaultWallet");
                        isDefault.setChecked(selectedWallet.getWalletId().equals(defaultWalletId));
                    }
                });

    }
}