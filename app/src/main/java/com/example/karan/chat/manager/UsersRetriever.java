package com.example.karan.chat.manager;

import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.example.karan.chat.utils.ListDataProgressListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by karan on 1/7/2018.
 */

public class UsersRetriever implements ChildEventListener,ValueEventListener {

    private ListDataProgressListener listDataProgressListener;

    public UsersRetriever(ListDataProgressListener listDataProgressListener) {
        this.listDataProgressListener = listDataProgressListener;
        this.listDataProgressListener.onListDataFetchStarted();
    }


    public Query getUserProfiles(){

        listDataProgressListener.onListDataFetchStarted();

        Query query= FirebaseDatabase.getInstance()
                    .getReference()
                    .child(Constants.KEY_USERS);

        query.addChildEventListener(this);
        query.addListenerForSingleValueEvent(this);

        return query;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Users users=dataSnapshot.getValue(Users.class);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.listDataProgressListener.onListDataLoaded();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        this.listDataProgressListener.onListDataLoadingCancelled();
    }
}
