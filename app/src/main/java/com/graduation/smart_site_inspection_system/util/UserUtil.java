package com.graduation.smart_site_inspection_system.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.graduation.smart_site_inspection_system.Bean.UserBean;

public class UserUtil {

    static private UserBean user;
    public static UserBean getUser(){
        if(user==null)
            user=new UserBean();
        return user;
    }
    static void setUser(UserBean user){
        UserUtil.user =user;
        saveLoggedInUserName(user.getName());
    }
    /**
     * 获取是否是登录状态
     * @return
     */
    public static boolean isLoggedIn(){
        return !getLoggedInUserName().isEmpty();
    }

    /**
     * 获取登录的用户名
     * @return
     */
    public static String getLoggedInUserName(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        return sp.getString("user name","");
    }

    public static void saveLoggedInUserName(String userName){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        sp.edit().putString("user name",userName).apply();
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
