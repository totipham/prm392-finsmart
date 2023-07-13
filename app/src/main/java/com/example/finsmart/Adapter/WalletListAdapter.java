package com.example.finsmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.WalletListHolder> {
    private List<Wallet> wallets;

    public WalletListAdapter(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    @NonNull
    @Override
    public WalletListHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.wallet_list_layout, parent, false);

        return new WalletListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListHolder holder, int position) {
        Currency currency = Currency.getInstance("VND");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        holder.tvWalletName.setText(wallets.get(position).getName() + " Wallet");
        holder.tvWalletBalance.setText(format.format(wallets.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class WalletListHolder extends RecyclerView.ViewHolder {
        TextView tvWalletName;
        TextView tvWalletBalance;

        public WalletListHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName = itemView.findViewById(R.id.tv_wallet_name);
            tvWalletBalance = itemView.findViewById(R.id.tv_wallet_balance);
        }
    }
}
