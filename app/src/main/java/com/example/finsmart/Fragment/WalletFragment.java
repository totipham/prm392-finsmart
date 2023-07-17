package com.example.finsmart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Adapter.EditWalletAdapter;
import com.example.finsmart.Interface.RecyclerViewClickListener;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment implements RecyclerViewClickListener {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    List<WalletWithCheck> walletList;
    Wallet selectedWallet;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance(String param1, String param2) {
        return new WalletFragment();
    }

    ImageButton imageButton;

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
        walletList = new ArrayList<>();
        loadWalletList();
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        // Inflate the layout for this fragment
        imageButton = (ImageButton) view.findViewById(R.id.add_wallet_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(
                        ((MainActivity) getActivity()).addNewWalletFragment
                        , "addWallet", "Add New Wallet");
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button) view.findViewById(R.id.btn_remove_wallet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWallet != null) {
                    db.collection("wallets").document(selectedWallet.getWalletId())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loadWalletList();

                                    HomeFragment homeFragment = ((MainActivity) getActivity()).homeFragment;
                                    homeFragment.loadWalletList();

                                    Toast.makeText(getContext(), "Delete wallet successfully", Toast.LENGTH_SHORT).show();

                                    if ((walletList.size() - 1) > 0) {
                                        selectedWallet = walletList.get(1).getWallet();
                                    } else {
                                        selectedWallet = null;
                                    }
                                }
                            });

                    //if user default wallet is selected wallet id, then delete it
                    db.collection("users").document(mUser.getUid()).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String defaultWalletId = task.getResult().getString("defaultWallet");
                                    if (defaultWalletId.equals(selectedWallet.getWalletId())) {
                                        db.collection("users").document(mUser.getUid()).update("defaultWallet", "");
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Please select a wallet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((Button) view.findViewById(R.id.btn_edit_wallet)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedWallet != null) {
                    ((MainActivity) getActivity()).replaceFragment(
                            ((MainActivity) getActivity()).editWalletFragment
                            , "editWallet", "Edit Wallet");

                    ((EditWalletFragment) ((MainActivity) getActivity()).editWalletFragment).setWallet(selectedWallet);
                } else {
                    Toast.makeText(getContext(), "Please select a wallet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadWalletList() {
        db.collection("wallets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            walletList.clear();
                            boolean isFirst = true;

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("belongTo").equals(mUser.getUid())) {
                                    Wallet wallet = new Wallet(document.getId(), document.getString("name"), document.getDouble("balance"), document.getString("belongTo"));

                                    if (isFirst) {
                                        selectedWallet = wallet;
                                    }

                                    walletList.add(new WalletWithCheck(wallet, isFirst));
                                    isFirst = false;
                                }
                            }

                            ((TextView) getView().findViewById(R.id.tv_my_wallet)).setText("My Wallets" + " (" + walletList.size() + ")");

                            updateWalletRecyclerView(0);
                        } else {
                            Toast.makeText(getContext(), "Error getting wallets", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateWalletRecyclerView(int position) {
        RecyclerView walletScrollList = (RecyclerView) getView().findViewById(R.id.rview_wallet_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(position);

        EditWalletAdapter walletListAdapter = new EditWalletAdapter(walletList, this);
        walletScrollList.setAdapter(walletListAdapter);
        walletScrollList.setLayoutManager(layoutManager);

//        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        selectedWallet = walletList.get(position).getWallet();
        for (int i = 0; i < walletList.size(); i++) {
            walletList.get(i).setChecked(false);
        }
        walletList.get(position).setChecked(true);
        updateWalletRecyclerView(position);
    }
}