package com.example.karan.chat.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.karan.chat.FirebaseManager.SignUpManager;
import com.example.karan.chat.activities.UserDetailActivity;
import com.example.karan.chat.common.SharedPref;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.CommonUtils;
import com.example.karan.chat.R;
import com.example.karan.chat.utils.Constants;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by karan on 12/17/2017.
 */

public class SignUpFragment extends Fragment implements View.OnClickListener,SignUpManager.FirebaseSignUpCompleteListener {

    private EditText mEmailField, mPassField, mNameField;
    Button mSignupButton;
    CircleImageView imageView;
    String email, password, name;
    ProgressDialog mProgressDialog;
    SignUpManager signUpManager;

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog=new ProgressDialog(getContext());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailField = view.findViewById(R.id.signUpEmailField);
        mPassField = view.findViewById(R.id.signUpPassField);
        mNameField = view.findViewById(R.id.nameField);

        mSignupButton = view.findViewById(R.id.sign_up_button);

        imageView = view.findViewById(R.id.ImageViewSignUp);


        mSignupButton.setOnClickListener(this);

    }

    /*private void signUp() {

        mProgressDialog.setTitle(R.string.register_progress_dialog_title);
        mProgressDialog.setMessage(String.valueOf(R.string.register_progress_dialog_message));
        mProgressDialog.setCanceledOnTouchOutside(false);

        email = mEmailField.getText().toString().trim();
        password = mPassField.getText().toString().trim();
        name = mNameField.getText().toString().trim();

        if (CommonUtils.isEmailValid(email, getActivity())) {
            mEmailField.setError(getString(R.string.email_error));
        }
        if (CommonUtils.isPasswordValid(password, getActivity())) {
            mPassField.setError(getString(R.string.password_error));
        }
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(getString(R.string.name_error));
        }

        mProgressDialog.show();
        // register user to firebase authentication
        // store name and email to firebase database
        FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(getContext());
        firebaseAuthentication.startSignUp(email, password, name, mProgressDialog);

    }*/


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.sign_up_button: {
                mProgressDialog.setTitle(R.string.register_progress_dialog_title);
                mProgressDialog.setMessage(String.valueOf(R.string.register_progress_dialog_message));
                mProgressDialog.setCanceledOnTouchOutside(false);

                email = mEmailField.getText().toString().trim();
                password = mPassField.getText().toString().trim();
                name = mNameField.getText().toString().trim();

                if (CommonUtils.isEmailValid(email, getActivity())) {
                    mEmailField.setError(getString(R.string.email_error));
                }
                if (CommonUtils.isPasswordValid(password, getActivity())) {
                    mPassField.setError(getString(R.string.password_error));
                }
                if (TextUtils.isEmpty(name)) {
                    mNameField.setError(getString(R.string.name_error));
                }

                mProgressDialog.show();
                signUpManager=new SignUpManager(this);
                signUpManager.signUpUser(name,email,password,getActivity());


                }
                break;
            }
        }

    @Override
    public void onSignUpSuccess(FirebaseUser user) {
        Users users=new Users();
        users.setEmail(email);
        users.setName(name);
        SharedPref.saveObjectToSharedPreference(getContext(), Constants.KEY_USER_INFO, Constants.KEY_USER_DATA, users);

        startActivity(new Intent(getContext(), UserDetailActivity.class));
        mProgressDialog.dismiss();
    }

    @Override
    public void onSignUpFailure() {
        mProgressDialog.dismiss();
    }
}

