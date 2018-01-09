package com.example.karan.chat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karan.chat.R;
import com.example.karan.chat.activities.MessageActivity;
import com.example.karan.chat.adapters.UsersViewHolder;
import com.example.karan.chat.adapters.UsersFirebaseAdapter;
import com.example.karan.chat.manager.UsersRetriever;
import com.example.karan.chat.model.Users;
import com.example.karan.chat.utils.Constants;
import com.example.karan.chat.utils.ListDataProgressListener;
import android.graphics.Color;

import com.example.karan.chat.utils.OnRecycleViewItemClickListener;
import com.google.firebase.database.Query;
import android.support.v4.widget.SwipeRefreshLayout;


/**
 * Created by karan on 1/7/2018.
 */

public class ContactsFragment extends Fragment implements ListDataProgressListener,SwipeRefreshLayout.OnRefreshListener,OnRecycleViewItemClickListener{

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    UsersRetriever usersRetriever;
    UsersFirebaseAdapter usersFirebaseAdapter;
    Query query;


    public static ContactsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout=view.findViewById(R.id.swipe);
        recyclerView=view.findViewById(R.id.users_list_recycler);

        usersRetriever=new UsersRetriever(this);
        query=usersRetriever.getUserProfiles();

        usersFirebaseAdapter=new UsersFirebaseAdapter(getContext(), Users.class,R.layout.user_item_view, UsersViewHolder.class,query,usersRetriever);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(usersFirebaseAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        usersFirebaseAdapter.setOnRecycleViewItemClickListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onListDataFetchStarted() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onListDataLoaded() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onListDataLoadingCancelled() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClicked(Bundle bundle) {
        Intent intent=new Intent(getContext(), MessageActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
