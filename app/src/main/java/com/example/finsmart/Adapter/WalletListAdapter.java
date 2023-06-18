package com.example.finsmart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;

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
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Wallet: " + wallets.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return new WalletListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletListHolder holder, int position) {
        holder.tvWalletName.setText(wallets.get(position).getName() + " Wallet");
        holder.tvWalletBalance.setText("$" + wallets.get(position).getBalance());
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
