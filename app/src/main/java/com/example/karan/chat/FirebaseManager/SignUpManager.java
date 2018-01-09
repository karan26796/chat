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

public class SignUpManager implements OnCompleteListener<AuthResult>,
        CreateUserProfileManager.OnUserProfileChangedListener {

    private FirebaseAuth firebaseAuth;

    private FirebaseSignUpCompleteListener signUpCompleteListener;

    private String username;

    public interface FirebaseSignUpCompleteListener {
        void onSignUpSuccess(FirebaseUser user);

        void onSignUpFailure();
    }

    public SignUpManager(FirebaseSignUpCompleteListener signUpCompleteListener) {
        //Get Firebase firebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();
        this.signUpCompleteListener = signUpCompleteListener;
    }

    public void signUpUser(String username, String userEmail, String password,Activity activity) {
        this.username = username;
        //create user
        firebaseAuth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener(this);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String UID = currentUser.getUid();
        // create a database reference for the user
        //userEmailVerification();
        Users user = new Users();
        user.setEmail(userEmail);
        user.setName(username);
        SharedPref.saveObjectToSharedPreference(activity, Constants.KEY_USER_INFO, Constants.KEY_USER_DATA, user);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user = task.getResult().getUser();
        CreateUserProfileManager createUserProfileManager = new CreateUserProfileManager(this);
        createUserProfileManager.createFirebaseUserProfile(user, username);
    }

    @Override
    public void onUserProfileChanged(String username) {
        signUpCompleteListener.onSignUpSuccess(firebaseAuth.getCurrentUser());
    }

    @Override
    public void onUserProfileChangeFailed() {
        signUpCompleteListener.onSignUpFailure();
    }

}
