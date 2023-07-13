package com.example.finsmart.Fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.Adapter.TransactionListAdapter;
import com.example.finsmart.Adapter.WalletListAdapter;
import com.example.finsmart.Model.Transaction;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ViewPager2 viewPager;

    public TextView name;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateWalletList(view);
        generateTransactionList(view);
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

        Wallet w1 = new Wallet("w1", "Shopping", 1000000);
        Wallet w2 = new Wallet("w2", "Education", 1000000);
        Wallet w3 = new Wallet("w3", "Investment", 1000000);

        walletList.add(w1);
        walletList.add(w2);
        walletList.add(w3);

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