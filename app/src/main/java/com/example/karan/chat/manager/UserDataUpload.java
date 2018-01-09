package com.example.karan.chat.manager;

import android.support.annotation.NonNull;
import android.net.Uri;
import android.widget.Toast;

import com.example.karan.chat.ChatApp;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by karan on 1/8/2018.
 */

public class UserDataUpload implements OnProgressListener<UploadTask.TaskSnapshot>,
        OnPausedListener<UploadTask.TaskSnapshot>,
        OnSuccessListener<UploadTask.TaskSnapshot>,
        OnFailureListener{

    private Users users;
    UploadTask uploadTask;
    UserDataUploadListener userDataUploadListener;
    private static UserDataUpload mInstance;

    public interface UserDataUploadListener {

        void onDataUploadCompeted();
    }

    public UserDataUpload() {
    }

    public static UserDataUpload getInstance(){
        if (null==mInstance){
            synchronized (UserDataUpload.class){
                if (null==mInstance){
                    mInstance=new UserDataUpload();
                }
            }
        }
        return mInstance;
    }

    public void setUserDataUploadListener(UserDataUploadListener userDataUploadListener){
        this.userDataUploadListener=userDataUploadListener;
    }

    public void uploadUserData(String userName,String userEmail,Uri profileImage){
        Users users=new Users();

        users.setName(userName);
        users.setEmail(userEmail);

        uploadImage(profileImage);
    }

    private void uploadUserDetailsWithImageUrl(String imageUrl) {
        users.setImageUrl(imageUrl);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        DatabaseReference restaurantRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.KEY_USERS)
                .child(uid);

        DatabaseReference pushRef = restaurantRef.push();
        String pushId = pushRef.getKey();
        users.setPushID(pushId);
        pushRef.setValue(users);

        if (null != userDataUploadListener) {
            userDataUploadListener.onDataUploadCompeted();
        }
    }

    public void uploadImage(Uri profileImage){

        FirebaseStorage storage=FirebaseStorage.getInstance();

        StorageReference storageReference=storage.getReferenceFromUrl(Constants.APP_URL);

        StorageMetadata metadata= new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        uploadTask=storageReference
                .child("images/"+profileImage.getLastPathSegment())
                .putFile(profileImage,metadata);

        uploadTask.addOnProgressListener(this)
                .addOnPausedListener(this)
                .addOnFailureListener(this)
                .addOnSuccessListener(this);
    }

    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        double progress =(100*(double) taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
        Toast.makeText(ChatApp.getInstance(), Math.round(progress)+"%"+"uploaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
        Toast.makeText(ChatApp.getInstance(), "Upload paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Uri downloadUri=taskSnapshot.getDownloadUrl();
        uploadUserDetailsWithImageUrl(downloadUri.toString());
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(ChatApp.getInstance(), "Failed", Toast.LENGTH_SHORT).show();
    }
}
