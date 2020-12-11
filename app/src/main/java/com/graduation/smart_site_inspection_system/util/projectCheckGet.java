package com.graduation.smart_site_inspection_system.util;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 *  接口1：获取项目
 *  传过去：用户id
 *  返回：组长：项目组所有未检查的项目的所有信息。组员：所有未检查和未通过的项目的所有信息
 */
public class projectCheckGet extends Thread{
    public static final int Msg_projectCheckGet_what = 31;  //msg.what
    public static final String Msg_projectCheckGet_String = "projectCheckGet";  //msg_string

    private String account;
    private Handler handler;

    public projectCheckGet(Handler handler, String account) {
        this.account = account;
        this.handler = handler;
    }

    @Override
    public void run() {
        try{
            //传递的数据
            String result = HttpUtil.projectCheck_Get(account);
            Bundle bundle = new Bundle();
            bundle.putString(Msg_projectCheckGet_String, result);

            //发送数据
            Message message = Message.obtain();
            message.setData(bundle);   //message.obj=bundle 传值也行
            message.what = Msg_projectCheckGet_what;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
