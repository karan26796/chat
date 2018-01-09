package com.example.karan.chat.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karan.chat.R;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;

import com.squareup.picasso.Picasso;

public class MessageActivity extends BaseActivity {

    TextView userName,userEmail;
    ImageView profileImage;
    Bundle bundle;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImage=findViewById(R.id.profileImage);

        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userEmail);
        getData();

        setData();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        bundle=getIntent().getExtras();
        users=bundle.getParcelable(Constants.KEY_USER_INFO);

    }

    private void setData(){
        userEmail.setText(users.getEmail());
        userName.setText(users.getName());
        Picasso.with(this)
                .load(users.getImageUrl())
                .into(profileImage);
        Toast.makeText(this, users.getImageUrl()+"", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getToolbarID() {
        return R.id.message_activity_toolbar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
