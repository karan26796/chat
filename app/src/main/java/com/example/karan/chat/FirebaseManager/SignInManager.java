package com.example.karan.chat.FirebaseManager;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.karan.chat.common.SharedPref;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by karan on 1/9/2018.
 */

public class SignInManager implements OnCompleteListener<AuthResult> {

    private FirebaseAuth firebaseAuth;

    private FirebaseSignInCompleteListener firebaseSignInCompleteListener;

    private Users users;

    public SignInManager(FirebaseSignInCompleteListener firebaseSignInCompleteListener) {
        firebaseAuth=FirebaseAuth.getInstance();
        this.firebaseSignInCompleteListener = firebaseSignInCompleteListener;
    }

    public interface FirebaseSignInCompleteListener{

        void onSignInSuccess(FirebaseUser user);

        void onSignInFailure();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user =firebaseAuth.getCurrentUser();

        if (user!=null){
            firebaseSignInCompleteListener.onSignInSuccess(user);
        }else {
            firebaseSignInCompleteListener.onSignInFailure();
        }
    }

    public void signInUser(String userEmail,String password){
        firebaseAuth.signInWithEmailAndPassword(userEmail,password).addOnCompleteListener(this);
    }
}
