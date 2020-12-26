package com.graduation.smart_site_inspection_system.util;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**    上传审核检查结果
 checkId	审核特定的检查结果
 flag	审核结果：1为通过；2为未通过
 */
public class reviewCheckPost extends Thread{
    public static final int Msg_reviewCheckPost_what = 61;  //msg.what
    public static final String Msg_reviewCheckPost_String = "reviewCheckPost";  //msg_string

    private String checkId;
    private String flag;
    private Handler handler;

    public reviewCheckPost(String checkId, String flag, Handler handler) {
        this.checkId = checkId;
        this.flag = flag;
        this.handler = handler;
    }

    @Override
    public void run() {
        try{
            //传递的数据
            HashMap<String,String> options=new HashMap<>();
            options.put("checkId", checkId);
            options.put("flag", flag);
            boolean result = HttpUtil.reviewCheckPost(options);


            //发送数据
            Message message = Message.obtain();
            message.obj=result;
            message.what = Msg_reviewCheckPost_what;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
