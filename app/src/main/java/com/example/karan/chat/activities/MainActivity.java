package com.example.karan.chat.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.karan.chat.R;
import com.example.karan.chat.adapters.HomePageAdapter;
import com.example.karan.chat.common.SharedPref;
import com.example.karan.chat.fragments.ContactsFragment;
import com.example.karan.chat.fragments.FriendsFragment;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Users users;
    TextView mUserName,mUserEmail;
    ViewPager homePageViewPager;
    TabLayout homePageTabLayout;
    HomePageAdapter homePageAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        homePageTabLayout=findViewById(R.id.main_activity_tab_layout);
        homePageViewPager=findViewById(R.id.homePageViewPager);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUpViewPager(homePageViewPager);
        mAuth=FirebaseAuth.getInstance();
        homePageTabLayout.setupWithViewPager(homePageViewPager);

        NavigationView navigationView =findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);

        mUserName=hView.findViewById(R.id.nav_user_name);
        mUserEmail=hView.findViewById(R.id.nav_user_email);

        users=new Users();
        users = SharedPref.getSavedObjectFromPreference(MainActivity.this, Constants.KEY_USER_INFO, Constants.KEY_USER_DATA,Users.class);
        setUserProfile();

        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setUpViewPager(ViewPager upViewPager){
        this.homePageViewPager=upViewPager;

        homePageAdapter=new HomePageAdapter(getSupportFragmentManager());
        homePageAdapter.addFragment(FriendsFragment.newInstance(), "Friends");
        homePageAdapter.addFragment(ContactsFragment.newInstance(),"Contacts");
        upViewPager.setAdapter(homePageAdapter);
    }

    public void setUserProfile(){
        if (users!=null){
            mUserEmail.setText(users.getEmail());
            mUserName.setText(users.getName());
        }
        else{
            mUserName.setText("Please Login");
            mUserEmail.setText("Tap here to Login");
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }
        else if(id==R.id.nav_logout){
            logoutAction();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutAction(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Logout?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).show();
    }

    @Override
    protected int getToolbarID() {
        return R.id.main_activity_toolbar;
    }
}
