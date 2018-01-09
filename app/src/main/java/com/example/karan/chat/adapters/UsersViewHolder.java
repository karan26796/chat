package com.example.karan.chat.adapters;

import com.squareup.picasso.Picasso;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.ImageView;
import com.example.karan.chat.R;
import android.view.MenuInflater;
import com.example.karan.chat.model.Users;
import android.support.v7.widget.PopupMenu;
/**
 * Created by karan on 1/7/2018.
 */

public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    private PopupMenu popupMenu;
    View itemView;
    private TextView mUserProfileName,mUserProfileEmail,userInitials;
    private ImageView userProfileImage,optionsButton;

    public UsersViewHolder(View itemView) {
        super(itemView);

        this.itemView=itemView;

        optionsButton=itemView.findViewById(R.id.options_button);
        mUserProfileName=itemView.findViewById(R.id.userProfileName);
        mUserProfileEmail=itemView.findViewById(R.id.userProfileEmail);
        userInitials= itemView.findViewById(R.id.nameInitials);
        userProfileImage=itemView.findViewById(R.id.user_profile_image);

        optionsButton.setOnClickListener(this);
    }

    void bindUserDetails(Users users){

        Picasso.with(itemView.getContext())
                .load(users.getImageUrl())
                .error(R.drawable.button_bg)
                .into(userProfileImage);

        //userProfileImage.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
        //userProfileImage.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.colorAccent));
        mUserProfileEmail.setText(users.getEmail());
        userInitials.setText(users.getName().substring(0,1));
        mUserProfileName.setText(users.getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.options_button:
                showPopup();
        }
    }

    private void showPopup(){
        popupMenu=new PopupMenu(itemView.getContext(),itemView);
        popupMenu.setGravity(Gravity.END);
        MenuInflater inflater=popupMenu.getMenuInflater();

        inflater.inflate(R.menu.user_popup,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
