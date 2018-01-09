package com.example.karan.chat.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.karan.chat.R;
import com.example.karan.chat.adapters.LoginViewPagerAdapter;
import com.example.karan.chat.common.SharedPref;
import com.example.karan.chat.fragments.SignInFragment;
import com.example.karan.chat.fragments.SignUpFragment;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    LoginViewPagerAdapter loginViewPagerAdapter;
    FirebaseAuth mAuth;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpToolbar();

        mAuth=FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager=findViewById(R.id.homeViewPager);
        tabLayout=findViewById(R.id.tabLayout);
        setUpViewPager(viewPager);
        users=new Users();
        users = SharedPref.getSavedObjectFromPreference(LoginActivity.this, Constants.KEY_USER_INFO, Constants.KEY_USER_DATA,Users.class);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();

        if (firebaseUser==null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    public void setUpViewPager(ViewPager upViewPager) {
        this.viewPager = upViewPager;

        loginViewPagerAdapter=new LoginViewPagerAdapter(getSupportFragmentManager());
        loginViewPagerAdapter.addFragment(SignInFragment.newInstance(),"Sign In");
        loginViewPagerAdapter.addFragment(SignUpFragment.newInstance(),"Sign Up");
        upViewPager.setAdapter(loginViewPagerAdapter);
    }

    @Override
    protected int getToolbarID() {
        return R.id.login_activity_toolbar;
    }
}
