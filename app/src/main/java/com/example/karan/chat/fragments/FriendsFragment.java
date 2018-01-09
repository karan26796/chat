package com.example.karan.chat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;

import com.example.karan.chat.utils.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.example.karan.chat.R;
import com.example.karan.chat.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by karan on 1/7/2018.
 */

public class FriendsFragment extends Fragment implements View.OnClickListener{

    ImageView imageView;
    FirebaseAuth mAuth;
    public static FriendsFragment newInstance() {

        Bundle args = new Bundle();

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView=view.findViewById(R.id.imageView);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =mAuth.getCurrentUser();
        String UID =firebaseUser.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                .getReference()
                .child(Constants.KEY_USERS)
                .child(UID)
                .child("imageURL");

        Picasso.with(getContext())
                .load(databaseReference.toString())
                .into(imageView);
        imageView.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
    }

}
