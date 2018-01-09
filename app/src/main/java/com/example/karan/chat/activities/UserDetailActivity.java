package com.example.karan.chat.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karan.chat.R;
import com.example.karan.chat.manager.UserDataUpload;

import java.io.IOException;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener,UserDataUpload.UserDataUploadListener {

    private static final int PICK_IMAGE_REQUEST = 234;
    Button mUploadImage;
    TextView userName,userEmail;
    ImageView userProfileImage;
    Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setUpToolbar();
        userName=findViewById(R.id.nameUser);
        userEmail=findViewById(R.id.emailUser);

        userProfileImage=findViewById(R.id.profileImageUser);
        mUploadImage=findViewById(R.id.uploadButton);

        mUploadImage.setOnClickListener(this);
        userProfileImage.setOnClickListener(this);
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                userProfileImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadUserImage(){
        UserDataUpload userDataUpload=UserDataUpload.getInstance();
        userDataUpload.setUserDataUploadListener(this);
        userDataUpload.uploadUserData(userName.getText().toString(),userEmail.getText().toString(),filePath);
    }

    @Override
    protected int getToolbarID() {
        return R.id.user_detail_toolbar;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.profileImageUser:
                showFileChooser();
                break;

            case R.id.uploadButton:
                uploadUserImage();
                break;
        }

    }

    @Override
    public void onDataUploadCompeted() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
