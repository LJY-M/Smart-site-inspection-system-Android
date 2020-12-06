package com.graduation.smart_site_inspection_system.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class UserUtil {
    /**
     * 获取是否是登录状态
     * @param context
     * @return
     */
    public static boolean isLoggedIn(Context context){
        return !getLoggedInUserName(context).isEmpty();
    }

    /**
     * 获取登录的用户名
     * @param context
     * @return
     */
    public static String getLoggedInUserName(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("user name","");
    }

    public static void saveLoggedInUserName(Context context,String userName){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("user name",userName).apply();
    }

    /**
     * 获取保存在本地的token
     * @param context
     * @return
     */
    public static String getToken(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString("token","");
    }

    public static void saveToken(Context context,String token){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("token",token).apply();
    }
}
