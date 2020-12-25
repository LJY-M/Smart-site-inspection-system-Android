package com.graduation.smart_site_inspection_system.util;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;

import java.util.HashMap;
import java.util.List;

/**    上传检查结果
 id	指明更新的检查项目ID
 projectId	填充内容
 groupId	填充内容
 userId	上传者的用户id
 checkSystemId	填充内容
 grade	打分
 description	描述检查结果
 */
public class uploadCheckPost extends Thread{
    public static final int Msg_uploadCheckPost_what = 41;  //msg.what
    public static final String Msg_uploadCheckPost_String = "uploadCheckPost";  //msg_string

    private String id;
    private String projectId;
    private String groupId;
    private String userId;
    private String checkSystemId;
    private String grade;
    private String description;
    private Handler handler;

    public uploadCheckPost(String id, String projectId, String groupId, String userId, String checkSystemId, String grade, String description, Handler handler) {
        this.id = id;
        this.projectId = projectId;
        this.groupId = groupId;
        this.userId = userId;
        this.checkSystemId = checkSystemId;
        this.grade = grade;
        this.description = description;
        this.handler = handler;
    }

    @Override
    public void run() {
        try{
            //传递的数据
            HashMap<String,String> options=new HashMap<>();
            options.put("id",id);
            options.put("projectId",projectId);
            options.put("groupId",groupId);
            options.put("userId",userId);
            options.put("checkSystemId",checkSystemId);
            options.put("grade",grade);
            options.put("description",description);
            boolean result = HttpUtil.uploadCheckPost(options);

            //发送数据
            Message message = Message.obtain();
            message.obj=result;
            message.what = Msg_uploadCheckPost_what;
            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
