package com.example.karan.chat.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by Deepak on 11/18/2017.
 */

public class Users implements Parcelable {

    private String name;
    private String email;
    private String imageUrl;
    private String pushID;

    public Users(){

    }

    protected Users(Parcel in) {
        name = in.readString();
        email = in.readString();
        imageUrl=in.readString();
        pushID=in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public void Users(String name, String email,String imageUrl,String pushID) {
        this.name = name;
        this.email = email;
        this.imageUrl=imageUrl;
        this.pushID=pushID;
    }


    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
    }

}
