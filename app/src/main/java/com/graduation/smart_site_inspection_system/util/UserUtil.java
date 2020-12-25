package com.graduation.smart_site_inspection_system.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.graduation.smart_site_inspection_system.Bean.UserBean;

public class UserUtil {

/*    static private UserBean user;
    public static UserBean getUser(){
        if(user==null)
            user=new UserBean();
        return user;
    }
    static void setUser(UserBean user){
        UserUtil.user =user;
        saveLoggedInAccount(user.getName());
    }*/
    /**
     * 获取是否是登录状态
     * @return
     */
    public static boolean isLoggedIn(){
        return !getLoggedInAccount().isEmpty();
    }

    public static void logout(){
        saveLoggedInAccount("");
    }

    /**
     * 获取登录的用户名
     * @return
     */
    public static String getLoggedInAccount(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getString("account","");
    }

    public static void saveLoggedInAccount(String account){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        sp.edit().putString("account",account).apply();
    }

    /**
     * 获取登录者id
     * @return
     */
    public static int getUserId(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getInt("user id",-1);
    }

    public static void saveUserId(int id){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        sp.edit().putInt("user id",id).apply();
    }

    /**
     * 获取保存在本地的token
     * @return
     */
    public static String getToken(){
        Context context=MyApplication.getContext();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getString("token","");
    }

    public static void saveToken(String token){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        sp.edit().putString("token",token).apply();
    }
}
