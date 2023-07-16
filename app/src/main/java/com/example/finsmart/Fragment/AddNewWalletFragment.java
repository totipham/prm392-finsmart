package com.example.finsmart.Fragment;

import android.content.Intent;
import android.os.Bundle;

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

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNewWalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewWalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    public AddNewWalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_new_card.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewWalletFragment newInstance(String param1, String param2) {
        AddNewWalletFragment fragment = new AddNewWalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View crap = inflater.inflate(R.layout.fragment_add_new_wallet, container, false);
        TextView name = (TextView) crap.findViewById(R.id.txt_wallet);
        EditText input = (EditText) crap.findViewById(R.id.edt_name);
        EditText amount = (EditText) crap.findViewById(R.id.edt_amount);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this example
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    name.setText("Name here");
                }else{
                    name.setText(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button confirm = (Button) crap.findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> card = new HashMap<>();
                card.put("name", name.getText().toString());
                card.put("balance", Float.parseFloat(amount.getText().toString()));
                card.put("belongTo",mUser.getUid());
                db.collection("wallets").add(card);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return crap;
    }
}