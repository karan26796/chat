package com.example.karan.chat;
import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by karan on 1/8/2018.
 */

public class ChatApp extends Application {

    private static ChatApp chatApp;

    @Override
    public void onCreate() {
        super.onCreate();

        chatApp=this;

        if(!FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    public static ChatApp getInstance(){
        return chatApp;
    }
}
