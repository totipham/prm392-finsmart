package com.example.finsmart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finsmart.Activity.MainActivity;
import com.example.finsmart.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmountTransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmountTransferFragment extends Fragment{

    private View mView;

    public AmountTransferFragment() {
        // Required empty public constructor
    }

    public static AmountTransferFragment newInstance() {
        return new AmountTransferFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_amount_transfer, container, false);

        EditText txtAmount = (EditText) mView.findViewById(R.id.editTextNumber);
        final Double[] maxAmount = new Double[1];
        getParentFragmentManager().setFragmentResultListener("maxAmountKey", this, (requestKey, bundle) -> maxAmount[0] = Double.parseDouble(bundle.getString("maxAmount")));

        mView.findViewById(R.id.btn_continue_amount).setOnClickListener(v -> {
            String amount = "";
            boolean isAmountValid = false;
            if(!TextUtils.isEmpty(txtAmount.getText())){
                amount = txtAmount.getText().toString();
                isAmountValid = !(maxAmount[0] < Double.parseDouble(amount));
            }
            if(isAmountValid){
                Bundle result = new Bundle();
                result.putString("amount", amount);
                getParentFragmentManager().setFragmentResult("amountKey", result);
                ((MainActivity) getActivity()).replaceFragment(((MainActivity) getActivity()).confirmTransferFragment, "confirmTransfer", "Transfer");
            }else{
                Toast.makeText(getContext(), "Invalid amount!", Toast.LENGTH_SHORT).show();
            }
        });

        //set text receiver name
        getParentFragmentManager().setFragmentResultListener("recipientNameKey1", this, (requestKey, bundle) ->
                ((TextView) mView.findViewById(R.id.txt_recipient_name)).setText(bundle.getString("recipientName")));
        //set text receiver email
        getParentFragmentManager().setFragmentResultListener("recipientMailKey1", this, (requestKey, bundle) ->
                ((TextView) mView.findViewById(R.id.txt_recipient_email)).setText(bundle.getString("recipientMail")));
        //set text receiver avatar
        getParentFragmentManager().setFragmentResultListener("recipientAvatarKey", this, (requestKey, bundle) ->
                (Picasso.get()
                        .load(bundle.getString("recipientAvatar").replace("http", "https")))
                        .into(((de.hdodenhof.circleimageview.CircleImageView) mView.findViewById(R.id.img_recipient_avatar))));

        return mView;
    }
}