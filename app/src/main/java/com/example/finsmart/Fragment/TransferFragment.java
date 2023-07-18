package com.example.finsmart.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Model.User;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.Currency;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    View mView;

    String recipientEmail, recipientWalletID;
    Double amount;
    final String[] walletID = new String[1];

    Button confirm;
    public TransferFragment() {
        // Required empty public constructor
    }

    public static TransferFragment newInstance() {
        return new TransferFragment();
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
        Currency currency = Currency.getInstance("VND");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);

        //get wallet id (walletID[0])

        getParentFragmentManager().setFragmentResultListener("walletIDKey", this, (requestKey, bundle) -> {
            walletID[0] = bundle.getString("walletID");
        });

        mView = inflater.inflate(R.layout.fragment_tranfer, container, false);
        //set text sender
        if (mUser != null) {
            DocumentReference docRef = db.collection("users").document(mUser.getUid());
            docRef.get().addOnCompleteListener(task -> {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ((TextView) mView.findViewById(R.id.txt_sender)).setText(document.getString("name"));
                }
            });
        }
        //set text receiver name
        getParentFragmentManager().setFragmentResultListener("recipientNameKey", this, (requestKey, bundle) ->
                ((TextView) mView.findViewById(R.id.txt_receiver)).setText(bundle.getString("recipientName")));
        //set text receiver email
        getParentFragmentManager().setFragmentResultListener("recipientMailKey", this, (requestKey, bundle) ->
        {

            recipientEmail = bundle.getString("recipientMail");
            ((TextView) mView.findViewById(R.id.txt_recipient_email)).setText(recipientEmail);
        });
        //set text amount
        getParentFragmentManager().setFragmentResultListener("amountKey", this, (requestKey, bundle) -> ((TextView) mView.findViewById(R.id.textView13)).setText(format.format(Double.parseDouble(bundle.getString("amount")))));

        confirm = mView.findViewById(R.id.btn_edit_wallet);

        //
        final String[] a = new String[1];
        getParentFragmentManager().setFragmentResultListener("recipientDefaultWalletIDKey", this, (requestKey, bundle) ->
        {

            a[0] = bundle.getString("recipientDefaultWalletID");
        });



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DocumentReference mineRef = db.collection("wallets").document(walletID[0]);
                mineRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                if(document.getDouble("balance") > 0){
                                    String amount = ((TextView) mView.findViewById(R.id.textView13)).getText().toString();
                                    amount = amount.replaceAll("[^\\d]", "");
                                    double amountDou = Double.parseDouble(amount)/100;
                                    double result = document.getDouble("balance") - amountDou;

                                    //update field value
                                    mineRef.update("balance", result);

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Log.d("Firebase", "Cannot find that wallet");
                            }
                        }
                    }
                });
                DocumentReference recipientRef = db.collection("wallets").document(a[0]);

                recipientRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                    String amount = ((TextView) mView.findViewById(R.id.textView13)).getText().toString();
                                    amount = amount.replaceAll("[^\\d]", "");
                                    double amountDou = Double.parseDouble(amount)/100;
                                    recipientWalletID = document.getString("defaultWallet");
                                    double result = document.getDouble("balance") + amountDou;

                                    //update field value
                                    recipientRef.update("balance", result);

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                            } else {
                                Log.d("Firebase", "Your recipient doesn't have wallet");
                            }
                        }
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return mView;

    }
}