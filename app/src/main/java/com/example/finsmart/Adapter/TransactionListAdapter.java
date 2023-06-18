package com.example.finsmart.Adapter;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finsmart.Model.Transaction;
import com.example.finsmart.R;

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListHolder> {

    List<Transaction> transactionList;

    public TransactionListAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new TransactionListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionListHolder holder, int position) {
        String transactionType = transactionList.get(position).getType().toString();
        holder.tvTransactionName.setText(transactionList.get(position).getName());
        holder.tvTransactionType.setText(transactionType.substring(0, 1) + transactionType.substring(1).toLowerCase());
        holder.tvSign.setText(transactionList.get(position).isIncome() ? "+" : "-");
        holder.tvAmount.setText(" $" + transactionList.get(position).getAmount());
        holder.ivWalletIcon.setImageResource(transactionList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    class TransactionListHolder extends RecyclerView.ViewHolder {
        TextView tvTransactionName;
        TextView tvTransactionType;
        TextView tvSign;
        TextView tvAmount;
        ImageView ivWalletIcon;

        public TransactionListHolder(@NonNull View itemView) {
            super(itemView);
            tvTransactionName = itemView.findViewById(R.id.tv_transaction_name);
            tvTransactionType = itemView.findViewById(R.id.tv_transaction_type);
            tvSign = itemView.findViewById(R.id.tv_transaction_sign);
            tvAmount = itemView.findViewById(R.id.tv_transaction_amount);
            ivWalletIcon = itemView.findViewById(R.id.iv_wallet_icon);
        }
    }
}
