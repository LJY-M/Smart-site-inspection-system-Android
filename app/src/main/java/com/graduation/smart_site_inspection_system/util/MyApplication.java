package com.graduation.smart_site_inspection_system.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.graduation.smart_site_inspection_system.views.login.LoginActivity;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void reLogin(){
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}