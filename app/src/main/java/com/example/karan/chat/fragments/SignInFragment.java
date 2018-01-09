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
import android.widget.TextView;

import com.example.karan.chat.FirebaseManager.SignInManager;
import com.example.karan.chat.R;
import com.example.karan.chat.activities.MainActivity;
import com.example.karan.chat.common.SharedPref;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by karan on 12/17/2017.
 */

public class SignInFragment extends Fragment implements View.OnClickListener,
        SignInManager.FirebaseSignInCompleteListener{

    TextView mForgotPass;
    private EditText mEmailField, mPassField;
    Button mSignInButton;
    CircleImageView imageView;
    ProgressDialog mProgressDialog;
    SignInManager signInManager;
    String email, password;

    public static SignInFragment newInstance() {

        Bundle args = new Bundle();

        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mForgotPass = view.findViewById(R.id.forgotPass);

        mEmailField = view.findViewById(R.id.signInEmailField);
        mPassField = view.findViewById(R.id.signInPassField);

        mSignInButton = view.findViewById(R.id.sign_in_button);

        imageView = view.findViewById(R.id.signin_image_view);

        mSignInButton.setOnClickListener(this);
        mForgotPass.setOnClickListener(this);

    }

    /*private void forgotPassword() {
        email = mEmailField.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.email_error));
            Toast.makeText(getContext(), getString(R.string.toast_empty_email),
                    Toast.LENGTH_SHORT).show();
        } else {
            mEmailField.setError(getString(R.string.empty_email_error));
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), getString(R.string.toast_password_reset_email),
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
    }*/


    /*private void startSignIn() {

        mProgressDialog.setTitle(getString(R.string.register_progress_dialog_title));
        mProgressDialog.setMessage(getString(R.string.logging_in));
        mProgressDialog.setCanceledOnTouchOutside(false);

        email = mEmailField.getText().toString().trim();
        password = mPassField.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                mEmailField.setError(getString(R.string.email_error));
            }
            if (TextUtils.isEmpty(password)) {
                mPassField.setError(getString(R.string.password_error));
            }

        } else {
            mProgressDialog.show();

            FirebaseAuthentication firebaseAuthentication = new FirebaseAuthentication(getContext());
            firebaseAuthentication.startSignIn(email, password, mProgressDialog);
        }
    }*/



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sign_in_button: {
                //startSignIn();
                email = mEmailField.getText().toString().trim();
                password = mPassField.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        mEmailField.setError(getString(R.string.email_error));
                    }
                    if (TextUtils.isEmpty(password)) {
                        mPassField.setError(getString(R.string.password_error));
                    }

                mProgressDialog.setTitle(getString(R.string.register_progress_dialog_title));
                mProgressDialog.setMessage(getString(R.string.logging_in));
                mProgressDialog.setCanceledOnTouchOutside(false);

                mProgressDialog.show();

                signInManager = new SignInManager(this);
                signInManager.signInUser(email, password);
            }
                break;

            case R.id.forgotPass:
                break;

        }
    }

    @Override
    public void onSignInSuccess(FirebaseUser user) {
        Users users=new Users();
        users.setEmail(email);
        SharedPref.saveObjectToSharedPreference(getContext(),users.getEmail(), Constants.KEY_USER_DATA, users);
        mProgressDialog.dismiss();

        startActivity(new Intent(getContext(), MainActivity.class));

    }

    @Override
    public void onSignInFailure() {
        mProgressDialog.dismiss();
    }
}
