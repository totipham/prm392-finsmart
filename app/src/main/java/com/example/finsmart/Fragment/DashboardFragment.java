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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Adapter.ChooseWalletAdapter;
import com.example.finsmart.Interface.RecyclerViewClickListener;
import com.example.finsmart.Model.User;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment implements RecyclerViewClickListener {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private List<WalletWithCheck> walletList;
    ProgressBar progressBar, progressBarRecipient;
    Button btnNext;
    EditText edtRecipientEmail;
    TextView txtRecipientName, txtRecipientEmail;
    ImageView imvRecipientAvatar;
    User recipient;
    boolean isRecipientSelected = false;
    Wallet selectedWallet;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
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

        View view = inflater.inflate(R.layout.fragment_transfer_target, container, false);

        progressBar = view.findViewById(R.id.pbar_wallet_2);
        progressBarRecipient = view.findViewById(R.id.progress_bar_recipient);
        btnNext = view.findViewById(R.id.btn_continue);
        edtRecipientEmail = view.findViewById(R.id.edt_recipient_email);
        txtRecipientName = view.findViewById(R.id.txt_recipient_name);
        txtRecipientEmail = view.findViewById(R.id.txt_recipient_email);
        imvRecipientAvatar = view.findViewById(R.id.img_recipient_avatar);

        progressBar.setVisibility(View.VISIBLE);
        walletList = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadWalletList();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecipientSelected) {
                    //pass recipient name
                    Bundle recipientName = new Bundle();
                    recipientName.putString("recipientName", (recipient.getName()));
                    getParentFragmentManager().setFragmentResult("recipientNameKey", recipientName);
                    //
                    Bundle recipientMail = new Bundle();
                    recipientMail.putString("recipientMail", (recipient.getEmail()));
                    getParentFragmentManager().setFragmentResult("recipientMailKey", recipientMail);

                    Bundle recipientAvatar = new Bundle();
                    recipientMail.putString("recipientAvatar", (recipient.getAvatar()));
                    getParentFragmentManager().setFragmentResult("recipientAvatarKey", recipientMail);
                    //Move to confirm transfer fragment
                    ((MainActivity) getActivity()).replaceFragment(((MainActivity) getActivity()).amountTransferFragment, "amountTransfer", "Transfer");
                } else {
                    progressBarRecipient.setVisibility(View.VISIBLE);
                    edtRecipientEmail.setText(edtRecipientEmail.getText().toString().trim());

                    //get user from firebase firestore by email
                    db.collection("users").
                            whereEqualTo("email", edtRecipientEmail.getText().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        //check if user exists
                                        if (task.getResult().getDocuments().size() == 0) {
                                            Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        //check if user is current user
                                        if (task.getResult().getDocuments().get(0).getId().equals(mUser.getUid())) {
                                            Toast.makeText(getContext(), "You can't transfer to yourself", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        String recipientId = task.getResult().getDocuments().get(0).getId();
                                        String recipientName = task.getResult().getDocuments().get(0).getString("name");
                                        String recipientEmail = task.getResult().getDocuments().get(0).getString("email");
                                        String recipientAvatar = task.getResult().getDocuments().get(0).getString("avatar");

                                        recipient = new User(recipientId, recipientName, recipientEmail, recipientAvatar);

                                        isRecipientSelected = true;
                                        btnNext.setText("Confirm");

                                        bindRecipient();
                                    } else {
                                        Toast.makeText(getContext(), "Error getting user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void bindRecipient() {
        //set visible
        txtRecipientName.setVisibility(View.VISIBLE);
        txtRecipientEmail.setVisibility(View.VISIBLE);
        imvRecipientAvatar.setVisibility(View.VISIBLE);
        progressBarRecipient.setVisibility(View.GONE);

        //binding data
        txtRecipientName.setText(recipient.getName());
        txtRecipientEmail.setText(recipient.getEmail());
        Picasso.get().load(recipient.getAvatar().replace("http", "https")).into(imvRecipientAvatar);
    }

    public void loadWalletList() {
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
                                    walletList.add(new WalletWithCheck(wallet, isFirst));
                                    isFirst = false;
                                }
                            }

                            updateWalletRecyclerView(0);
                        } else {
                            Toast.makeText(getContext(), "Error getting wallets", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateWalletRecyclerView(int position) {

        if (walletList.size() == 0) {
            ((TextView) getView().findViewById(R.id.tv_no_wallet_transfer)).setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        ((TextView) getView().findViewById(R.id.tv_no_wallet_transfer)).setVisibility(View.GONE);
        RecyclerView walletScrollList = (RecyclerView) getView().findViewById(R.id.rview_wallet_scroll);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(position);

        ChooseWalletAdapter walletListAdapter = new ChooseWalletAdapter(walletList, this);
        walletScrollList.setAdapter(walletListAdapter);
        walletScrollList.setLayoutManager(layoutManager);

        progressBar.setVisibility(View.GONE);
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