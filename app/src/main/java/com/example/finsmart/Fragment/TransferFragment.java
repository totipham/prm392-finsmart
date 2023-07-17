package com.example.finsmart.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finsmart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransferFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    View mView;

    public TransferFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransferFragment newInstance(String param1, String param2) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tranfer, container, false);
        //set text sender
        if (mUser != null) {
            DocumentReference docRef = db.collection("users").document(mUser.getUid());
            docRef.get().addOnCompleteListener(task -> {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ((TextView) mView.findViewById(R.id.txt_sender)).setText(document.getString("name"));
                }
            });
        }
        //set text receiver name
        getParentFragmentManager().setFragmentResultListener("recipientNameKey", this, (requestKey, bundle) -> ((TextView)mView.findViewById(R.id.txt_receiver)).setText(bundle.getString("recipientName")));
        //set text receiver email
        getParentFragmentManager().setFragmentResultListener("recipientMailKey", this, (requestKey, bundle) -> ((TextView)mView.findViewById(R.id.txt_recipient_email)).setText(bundle.getString("recipientMail")));
        //set text amount
        getParentFragmentManager().setFragmentResultListener("amountKey", this, (requestKey, bundle) -> ((TextView)mView.findViewById(R.id.textView13)).setText("$"+bundle.getString("amount")));

        // Inflate the layout for this fragment
        return mView;
    }
}