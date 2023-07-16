package com.example.finsmart.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Adapter.TransactionListAdapter;
import com.example.finsmart.Adapter.WalletListAdapter;
import com.example.finsmart.Model.Transaction;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ViewPager2 viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private float totalBalance = 0;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    private ArrayList<Wallet> walletList;
    ProgressBar progressBar;
    TextView wellcomename;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
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

        View view = inflater.inflate(R.layout.activity_home, container, false);
        progressBar = view.findViewById(R.id.pbar_wallet);
        progressBar.setVisibility(View.VISIBLE);
        walletList = new ArrayList<>();
        wellcomename = view.findViewById(R.id.tv_home_name);

        if (mUser != null) {
            DocumentReference docRef = db.collection("users").document(mUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        wellcomename.setText(document.getString("name") + "!");
                        loadWalletList();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateTransactionList(view);
    }

    private void loadWalletList() {
        db.collection("wallets").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    walletList.clear();

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //it is what it is
                        if (document.getString("belongTo").equals(mUser.getUid())) {
                            Wallet wallet = new Wallet(document.getId(), document.getString("name"), document.getDouble("balance"), document.getString("belongTo"));
                            totalBalance += wallet.getBalance();
                            walletList.add(wallet);
                        }
                    }

                    updateWalletRecyclerView();
                } else {
                    Toast.makeText(getContext(), "Error getting wallets", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateWalletRecyclerView() {
        Currency currency = Currency.getInstance("VND");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        ((TextView) getView().findViewById(R.id.tv_balance_amount)).setText(format.format(totalBalance));

        viewPager = (ViewPager2) getView().findViewById(R.id.view_pager);
        sliderDotspanel = (LinearLayout) getView().findViewById(R.id.slider_dots);
        WalletListAdapter walletListAdapter = new WalletListAdapter(walletList);
        viewPager.setAdapter(walletListAdapter);
        dotscount = walletListAdapter.getItemCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        progressBar.setVisibility(View.GONE);
    }


    void generateTransactionList(View view) {
        List<Transaction> transactionList = new ArrayList<>();

        Transaction t1 = new Transaction("t1", "AI-Bank", Transaction.TransactionType.DEPOSIT, false, "460.00", R.drawable.ticket_icon);
        Transaction t2 = new Transaction("t2", "McDonald", Transaction.TransactionType.PAYMENT, true, "34.10", R.drawable.ticket_icon);
        Transaction t3 = new Transaction("t3", "Gym", Transaction.TransactionType.DEPOSIT, false, "40.99", R.drawable.ticket_icon);
        Transaction t4 = new Transaction("t4", "AI-Bank", Transaction.TransactionType.DEPOSIT, false, "460.00", R.drawable.ticket_icon);

        transactionList.add(t1);
        transactionList.add(t2);
        transactionList.add(t3);
        transactionList.add(t4);

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
        RecyclerView transactionHistoryView = (RecyclerView) view.findViewById(R.id.rview_transaction_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        transactionHistoryView.setLayoutManager(layoutManager);
        transactionHistoryView.setAdapter(transactionListAdapter);
    }

    void generateWalletList(View view) {
        List<Wallet> walletList = new ArrayList<>();

//        Wallet w1 = new Wallet("w1", "Shopping", 1000000);
//        Wallet w2 = new Wallet("w2", "Education", 1000000);
//        Wallet w3 = new Wallet("w3", "Investment", 1000000);
//
//        walletList.add(w1);
//        walletList.add(w2);
//        walletList.add(w3);

        viewPager = (ViewPager2) view.findViewById(R.id.view_pager);
        sliderDotspanel = (LinearLayout) view.findViewById(R.id.slider_dots);

        WalletListAdapter walletListAdapter = new WalletListAdapter(walletList);

        viewPager.setAdapter(walletListAdapter);

        dotscount = walletListAdapter.getItemCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

//        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.noactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}