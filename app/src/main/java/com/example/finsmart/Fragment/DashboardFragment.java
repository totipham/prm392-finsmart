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

import com.example.finsmart.Adapter.ChooseRecipientAdapter;
import com.example.finsmart.Adapter.ChooseWalletAdapter;
import com.example.finsmart.Model.User;
import com.example.finsmart.Model.Wallet;
import com.example.finsmart.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        return inflater.inflate(R.layout.fragment_transfer_target, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateWalletScrollList(view);
        generateRecipientList(view);
    }

    void generateWalletScrollList(View view) {
        List<Wallet> walletList = new ArrayList<>();


        Wallet w1 = new Wallet("w1", "Shopping", 1000000);
        Wallet w2 = new Wallet("w2", "Education", 1000000);
        Wallet w3 = new Wallet("w3", "Investment", 1000000);

        walletList.add(w1);
        walletList.add(w2);
        walletList.add(w3);

        ChooseWalletAdapter walletListAdapter = new ChooseWalletAdapter(walletList);
        RecyclerView walletScrollList = (RecyclerView) view.findViewById(R.id.rview_wallet_scroll);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        walletScrollList.setLayoutManager(layoutManager);
        walletScrollList.setAdapter(walletListAdapter);
    }

    void generateRecipientList(View view) {
        List<User> recipientList = new ArrayList<>();

        User u1 = new User("u1", "John", "Nguyen", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");
        User u2 = new User("u2", "Jane", "Doe", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");
        User u3 = new User("u3", "Niece", "Smith", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");
        User u4 = new User("u4", "Nephew", "Smith", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");
        User u5 = new User("u5", "Niece", "Smith", "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200");

        recipientList.add(u1);
        recipientList.add(u2);
        recipientList.add(u3);
        recipientList.add(u4);
        recipientList.add(u5);

        ChooseRecipientAdapter recipientListAdapter = new ChooseRecipientAdapter(recipientList);
        RecyclerView recipientListScroll = (RecyclerView) view.findViewById(R.id.rview_recipient_scroll);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recipientListScroll.setLayoutManager(layoutManager);
        recipientListScroll.setAdapter(recipientListAdapter);

    }
}