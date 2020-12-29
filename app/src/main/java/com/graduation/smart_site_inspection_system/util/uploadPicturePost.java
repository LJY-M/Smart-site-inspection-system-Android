package com.graduation.smart_site_inspection_system.util;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;

/**
 * 上传图片
 * checkId	指明上传图片所属的检查项目
 * file	文件	上传图片
 */
public class uploadPicturePost extends Thread{
    public static final int Msg_uploadPicturePost_what = 71;  //msg.what
    public static final String Msg_uploadPicturePost_String = "uploadPicturePost";  //msg_string

    private String projectId;
    private String checkSystemId;
    private String file;
    private Handler handler;

    public uploadPicturePost(String projectId,String checkSystemId, String file, Handler handler) {
        this.projectId = projectId;
        this.checkSystemId=checkSystemId;
        this.file = file;
        this.handler = handler;
    }

    @Override
    public void run() {
        try{
            //传递的数据
            HashMap<String,String> options=new HashMap<>();
            options.put("projectId", projectId);
            options.put("checkSystemId",checkSystemId);
            options.put("file", file);
            boolean result = HttpUtil.uploadPicturePost(options);


            //发送数据
            Message message = Message.obtain();
            message.obj=result;
            message.what = Msg_uploadPicturePost_what;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
