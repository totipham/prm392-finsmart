package com.example.finsmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finsmart.Fragment.DashboardFragment;
import com.example.finsmart.Fragment.HomeFragment;
import com.example.finsmart.Fragment.WalletFragment;
import com.example.finsmart.Fragment.WalletWithCheck;
import com.example.finsmart.Interface.RecyclerViewClickListener;
import com.example.finsmart.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class EditWalletAdapter extends RecyclerView.Adapter<EditWalletAdapter.WalletListHolder> {
    private List<WalletWithCheck> wallets;
    private RecyclerViewClickListener listener;

    public EditWalletAdapter(List<WalletWithCheck> walletList, WalletFragment listener) {
        this.wallets = walletList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WalletListHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.edit_wallet_list_layout, parent, false);

        return new WalletListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListHolder holder, int position) {
        Currency currency = Currency.getInstance("VND");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        holder.tvWalletName.setText(wallets.get(position).getName());
//        holder.tvWalletBalance.setText(format.format(wallets.get(position).getBalance()));

        if (wallets.get(position).isChecked) {
            holder.imvCheck.setVisibility(View.VISIBLE);
        } else {
            holder.imvCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class WalletListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvWalletName;
        TextView tvWalletBalance;
        ImageView imvCheck;

        public WalletListHolder(@NonNull View itemView) {
            super(itemView);
            tvWalletName = itemView.findViewById(R.id.tv_wallet_name);
            tvWalletBalance = itemView.findViewById(R.id.tv_wallet_balance);
            imvCheck = itemView.findViewById(R.id.img_wallet_checked);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.recyclerViewListClicked(v, getAdapterPosition());
        }
    }
}
