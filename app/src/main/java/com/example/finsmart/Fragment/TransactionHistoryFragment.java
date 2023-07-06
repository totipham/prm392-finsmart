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

import com.example.finsmart.Adapter.TransactionListAdapter;
import com.example.finsmart.Model.Transaction;
import com.example.finsmart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionHistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionHistoryFragment newInstance(String param1, String param2) {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateTransactionList(view);
        generateTransactionList1(view);
    }

    void generateTransactionList(View view) {
        List<Transaction> transactionList = new ArrayList<>();

        Transaction t1 = new Transaction("t1", "Gym", Transaction.TransactionType.PAYMENT, false, "40.99", R.drawable.ticket_icon);
        Transaction t2 = new Transaction("t2", "AI-Bank", Transaction.TransactionType.DEPOSIT, true, "460.00", R.drawable.ticket_icon);
        Transaction t3 = new Transaction("t3", "McDonald", Transaction.TransactionType.PAYMENT, false, "34.10", R.drawable.ticket_icon);
        Transaction t4 = new Transaction("t4", "Recipient", Transaction.TransactionType.DEPOSIT, true, "320.19", R.drawable.ticket_icon);

        transactionList.add(t1);
        transactionList.add(t2);
        transactionList.add(t3);
        transactionList.add(t4);

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
        RecyclerView transactionHistoryView = (RecyclerView) view.findViewById(R.id.transaction_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        transactionHistoryView.setLayoutManager(layoutManager);
        transactionHistoryView.setAdapter(transactionListAdapter);
    }

    void generateTransactionList1(View view) {
        List<Transaction> transactionList = new ArrayList<>();

        Transaction t1 = new Transaction("t1", "Facebook Ads", Transaction.TransactionType.PAYMENT, false, "280.00", R.drawable.ticket_icon);
        Transaction t2 = new Transaction("t2", "Google Ads", Transaction.TransactionType.PAYMENT, false, "280.10", R.drawable.ticket_icon);

        transactionList.add(t1);
        transactionList.add(t2);

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
        RecyclerView transactionHistoryView = (RecyclerView) view.findViewById(R.id.transaction_history_date0906);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        transactionHistoryView.setLayoutManager(layoutManager);
        transactionHistoryView.setAdapter(transactionListAdapter);
    }
}