package com.example.finsmart.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finsmart.Model.User;
import com.example.finsmart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChooseRecipientAdapter extends RecyclerView.Adapter<ChooseRecipientAdapter.ChooseRecipientHolder> {
    private List<User> recipientList;

    public ChooseRecipientAdapter(List<User> recipientList) {
        this.recipientList = recipientList;

        Log.d("abc", recipientList.get(0).getLastName());
    }

    @NonNull
    @Override
    public ChooseRecipientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipient_list_layout, parent, false);
        return new ChooseRecipientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseRecipientHolder holder, int position) {
//        holder.imgAvatar.setImageURI(Uri.parse(recipientList.get(position).getAvatar()));
        Picasso.get().load(recipientList.get(position).getAvatar()).into(holder.imgAvatar);
        holder.tvFirstName.setText(recipientList.get(position).getFirstName());
        holder.tvLastName.setText(recipientList.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return recipientList.size();
    }

    public class ChooseRecipientHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvFirstName;
        TextView tvLastName;

        public ChooseRecipientHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvFirstName = itemView.findViewById(R.id.tv_firstname);
            tvLastName = itemView.findViewById(R.id.tv_lastname);
        }
    }
}
