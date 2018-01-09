package com.example.karan.chat.FirebaseManager;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by karan on 1/9/2018.
 */

public class CreateUserProfileManager implements OnCompleteListener<Void> {

    private FirebaseUser user;

    private OnUserProfileChangedListener onUserProfileChangedListener;

    public interface OnUserProfileChangedListener {

        void onUserProfileChanged(String username);

        void onUserProfileChangeFailed();
    }

    public void createFirebaseUserProfile(final FirebaseUser user,String userName){
        this.user=user;

        UserProfileChangeRequest addProfileName= new UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build();
        user.updateProfile(addProfileName).addOnCompleteListener(this);
    }

    public CreateUserProfileManager(OnUserProfileChangedListener onUserProfileChangedListener) {
        this.onUserProfileChangedListener = onUserProfileChangedListener;
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()){
            onUserProfileChangedListener.onUserProfileChanged(user.getDisplayName());
        }else {
            onUserProfileChangedListener.onUserProfileChangeFailed();
        }
    }
}
