package com.example.finsmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class ChooseWalletAdapter extends RecyclerView.Adapter<ChooseWalletAdapter.WalletListHolder> {
    private List<Wallet> wallets;

    public ChooseWalletAdapter(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    @NonNull
    @Override
    public WalletListHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.choose_wallet_list_layout, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams((int) (parent.getMeasuredWidth() * 0.85), ViewGroup.LayoutParams.MATCH_PARENT));

        return new WalletListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListHolder holder, int position) {
        Currency currency = Currency.getInstance("VND");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
//        format.format(1000000);
        holder.tvWalletName.setText(wallets.get(position).getName() + " Wallet");
        holder.tvWalletBalance.setText(format.format(wallets.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class WalletListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvWalletName;
        TextView tvWalletBalance;

        public WalletListHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName = itemView.findViewById(R.id.tv_wallet_name);
            tvWalletBalance = itemView.findViewById(R.id.tv_wallet_balance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Wallet: " + wallets.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
