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
import android.widget.Toast;

import com.example.finsmart.Activity.HomeActivity;
import com.example.finsmart.Adapter.TransactionListAdapter;
import com.example.finsmart.Model.Transaction;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
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
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
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
        db.collection("transactions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            transactionList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("BelongTo").equals(mUser.getUid())) {
                                    Transaction transaction = new Transaction(
                                            document.getString("BelongTo"),
                                            document.getDate("Date"),
                                            document.getString("Name"),
                                            Transaction.TransactionType.DEPOSIT,
                                            document.getBoolean("IsIncome"),
                                            document.getString("Amount"),
                                            R.drawable.ticket_icon
                                    );
                                    transactionList.add(transaction);
                                }
                            }

                            TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
                            RecyclerView transactionHistoryView = (RecyclerView) view.findViewById(R.id.rview_transaction_history);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            transactionHistoryView.setLayoutManager(layoutManager);
                            transactionHistoryView.setAdapter(transactionListAdapter);
                        } else {
                            Toast.makeText(getContext(), "Error loading transaction", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //cái này chưa đọng đên, thử bên trên trước - strong
    void generateTransactionList1(View view) {
        List<Transaction> transactionList = new ArrayList<>();

//        Transaction t1 = new Transaction("t1", "Facebook Ads", Transaction.TransactionType.PAYMENT, false, "280.00", R.drawable.ticket_icon);
//        Transaction t2 = new Transaction("t2", "Google Ads", Transaction.TransactionType.PAYMENT, false, "280.10", R.drawable.ticket_icon);
//
//        transactionList.add(t1);
//        transactionList.add(t2);

        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
        RecyclerView transactionHistoryView = (RecyclerView) view.findViewById(R.id.transaction_history_date0906);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        transactionHistoryView.setLayoutManager(layoutManager);
        transactionHistoryView.setAdapter(transactionListAdapter);
    }
}