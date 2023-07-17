package com.example.finsmart.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

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

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewWalletFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    SwitchCompat switchIsDefault;
    CollectionReference walletCollectionRef;
    String currentDefaultWalletId;

    public AddNewWalletFragment() {
        // Required empty public constructor
    }

    public static AddNewWalletFragment newInstance(String param1, String param2) {
        return new AddNewWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        currentDefaultWalletId = "";
        walletCollectionRef = db.collection("wallets");
        View view = inflater.inflate(R.layout.fragment_add_new_wallet, container, false);
        TextView name = view.findViewById(R.id.txt_wallet);
        EditText input = view.findViewById(R.id.edt_name);
        EditText amount = view.findViewById(R.id.edt_amount);
        switchIsDefault = view.findViewById(R.id.isDefault_swt);

        db.collection("users").document(mUser.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        currentDefaultWalletId = task.getResult().getString("defaultWallet");
                    }
                }
        );

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    name.setText("Name here");
                } else {
                    name.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button confirm = view.findViewById(R.id.btn_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> card = new HashMap<>();
                card.put("name", name.getText().toString());
                card.put("balance", Float.parseFloat(amount.getText().toString()));
                card.put("belongTo", mUser.getUid());

                walletCollectionRef.add(card).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        String walletId = task.getResult().getId();

                        if (switchIsDefault.isChecked() || currentDefaultWalletId == null || currentDefaultWalletId.equals("")) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("defaultWallet", walletId);
                            db.collection("users").document(mUser.getUid()).update(user);
                        }

                        //Show toast add success
                        Toast.makeText(getActivity(), "Add wallet success", Toast.LENGTH_SHORT).show();
                    }
                });

                //start intent MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}