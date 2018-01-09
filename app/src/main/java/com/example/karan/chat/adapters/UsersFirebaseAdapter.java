package com.example.karan.chat.adapters;

import android.content.Context;
import android.view.View;
import android.os.Bundle;

import com.example.karan.chat.utils.Constants;
import com.example.karan.chat.utils.OnRecycleViewItemClickListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import com.example.karan.chat.model.Users;

/**
 * Created by karan on 1/7/2018.
 */

public class UsersFirebaseAdapter extends FirebaseRecyclerAdapter<Users,UsersViewHolder> {

    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    Context mContext;
    private OnRecycleViewItemClickListener onRecycleViewItemClickListener;


    public UsersFirebaseAdapter(Context context, Class <Users> modelClass, int modelLayout,
                                Class<UsersViewHolder> viewHolderClass, Query mRef,
                                ChildEventListener childEventListener) {
        super(modelClass,modelLayout,viewHolderClass,mRef);

        mRef=mRef.getRef();
        mContext=context;
        mChildEventListener=childEventListener;
    }

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }

    @Override
    protected void populateViewHolder(final UsersViewHolder usersViewHolder, final Users model, final int position) {
        usersViewHolder.bindUserDetails(model);


        usersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putInt(Constants.POSITION,position);
                bundle.putParcelable(Constants.KEY_USER_INFO,model);
                if (null != onRecycleViewItemClickListener) {
                    onRecycleViewItemClickListener.onItemClicked(bundle);
                }
            }
        });
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mRef.removeEventListener(mChildEventListener);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        super.onCancelled(error);
    }
}
