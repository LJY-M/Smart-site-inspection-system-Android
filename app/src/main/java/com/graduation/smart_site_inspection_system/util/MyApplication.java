package com.graduation.smart_site_inspection_system.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
        UserUtil.logout();
        context.startActivity(new Intent(context, LoginActivity.class));
        Toast.makeText(context, "登录过期，请重新登录", Toast.LENGTH_LONG).show();
    }
}