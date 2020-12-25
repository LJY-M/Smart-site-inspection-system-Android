package com.graduation.smart_site_inspection_system.util;

import android.os.Handler;
import android.os.Message;

import com.graduation.smart_site_inspection_system.Bean.UserBean;

public class getUserPost extends Thread{
    public static final int Msg_getUserPost_what = 51;  //msg.what
    public static final String Msg_getUserPost_String = "getUserPost";  //msg_string

    private Handler handler;

    public getUserPost(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try{

            UserBean result = HttpUtil.getUser();

            //发送数据
            Message message = Message.obtain();
            message.obj=result;
            message.what = Msg_getUserPost_what;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
