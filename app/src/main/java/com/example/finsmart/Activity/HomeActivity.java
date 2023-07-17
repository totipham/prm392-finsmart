package com.example.finsmart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Adapter.TransactionListAdapter;
import com.example.finsmart.Adapter.WalletListAdapter;
import com.example.finsmart.Model.Transaction;
import com.example.finsmart.Model.Transaction.TransactionType;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    private ArrayList<Transaction> transactionList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Disable night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        setContentView(R.layout.activity_home);

        //generateWalletList();
        //generateTransactionList();
        GetTransactionData();
    }

    void generateTransactionList() {
//        Transaction t1 = new Transaction("t1", "AI-Bank", TransactionType.DEPOSIT, false, "460.00", R.drawable.ticket_icon);
//        Transaction t2 = new Transaction("t2", "McDonald", TransactionType.PAYMENT, true, "34.10", R.drawable.ticket_icon);
//        Transaction t3 = new Transaction("t3", "Gym", TransactionType.DEPOSIT, false, "40.99", R.drawable.ticket_icon);
//        Transaction t4 = new Transaction("t4", "AI-Bank", TransactionType.DEPOSIT, false, "460.00", R.drawable.ticket_icon);
//
//        transactionList.add(t1);
//        transactionList.add(t2);
//        transactionList.add(t3);
//        transactionList.add(t4);


    }

    //nhờ ae copy paste vào các trang còn lại
    void GetTransactionData(){
        transactionList = new ArrayList<>();
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
                                                TransactionType.DEPOSIT,
                                                document.getBoolean("IsIncome"),
                                                document.getString("Amount"),
                                                R.drawable.ticket_icon
                                                );
                                        transactionList.add(transaction);
                                    }
                                }

                                TransactionListAdapter transactionListAdapter = new TransactionListAdapter(transactionList);
                                RecyclerView transactionHistoryView = (RecyclerView) findViewById(R.id.rview_transaction_history);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
                                transactionHistoryView.setLayoutManager(layoutManager);
                                transactionHistoryView.setAdapter(transactionListAdapter);
                            } else {
                                Toast.makeText(HomeActivity.this, "Error loading transaction", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
    void generateWalletList() {
        List<Wallet> walletList = new ArrayList<>();

        viewPager = (ViewPager2) findViewById(R.id.view_pager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.slider_dots);

        WalletListAdapter walletListAdapter = new WalletListAdapter(walletList);

        viewPager.setAdapter(walletListAdapter);

        dotscount = walletListAdapter.getItemCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.noactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}