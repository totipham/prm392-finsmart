package com.example.finsmart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmountTransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmountTransferFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mView;

    public AmountTransferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Amount_transfer.
     */
    // TODO: Rename and change types and number of parameters
    public static AmountTransferFragment newInstance(String param1, String param2) {
        AmountTransferFragment fragment = new AmountTransferFragment();
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
        mView = inflater.inflate(R.layout.fragment_amount_transfer, container, false);
        mView.findViewById(R.id.btn_continue_amount).setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putString("amount", ((EditText)mView.findViewById(R.id.editTextNumber)).getText().toString());
            getParentFragmentManager().setFragmentResult("amountKey", result);
            ((MainActivity)getActivity()).replaceFragment(((MainActivity)getActivity()).confirmTransferFragment,"confirmTransfer","Transfer");
        });

        // Inflate the layout for this fragment
        return mView;
    }
}