package com.example.karan.chat.FirebaseManager;

import com.google.firebase.auth.FirebaseUser;

/**
 * Listener to listen firebase auth
 *
 * @author Nayanesh Gupte
 */
public interface FirebaseAuthCompleteListener {
    void onAuthSuccess(FirebaseUser user);

    void onAuthFailure();
}