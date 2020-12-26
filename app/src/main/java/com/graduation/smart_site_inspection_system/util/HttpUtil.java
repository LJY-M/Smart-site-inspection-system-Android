package com.graduation.smart_site_inspection_system.util;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectTree.ClientBean;
import com.graduation.smart_site_inspection_system.Bean.UserBean;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    //    服务器地址
    private static final String SERVER = "http://39.106.66.219:8080";//"http://39.106.66.219:3000/mock/11";
//    private static final String SERVER = "http://39.106.66.219:3000/mock/11";

    //http操作
    private static String HttpPost(String subUrl, @Nullable HashMap<String, String> options, @Nullable String content_type) {
        String address = SERVER + subUrl;
        String result = null;
        try {
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Authorization", UserUtil.getToken());
            if (content_type != null && !content_type.isEmpty())
                conn.setRequestProperty("Content-type", content_type);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            if (options != null) {
                int count = 0;
                for (Map.Entry<String, String> option : options.entrySet()) {
                    String data = "";
                    if (++count > 1)
                        data += "&";
                    data += (option.getKey() + "=" + option.getValue());
                    out.write(data.getBytes());
                }
            }
            out.flush();
            out.close();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                result = new String(message.toByteArray());
                String token = conn.getHeaderField("Authorization");
                if (token != null && !token.isEmpty())
                    UserUtil.saveToken(token);
            } else if (conn.getResponseCode() == 2008) {
                MyApplication.reLogin();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String HttpGet(String subUrl, @Nullable HashMap<String, String> options, @Nullable String content_type) {
        String address = SERVER + subUrl;
        String result = null;
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestProperty("Authorization", UserUtil.getToken());
            if (content_type != null && !content_type.isEmpty())
                connection.setRequestProperty("Content-type", content_type);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                result = new String(message.toByteArray());
            } else if (connection.getResponseCode() == 2008) {
                MyApplication.reLogin();
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<GroupBean, List<ProjectCheckBean>> projectCheck_Get() {
        HashMap<String, String> options = new HashMap<>();
        options.put("userId", String.valueOf(UserUtil.getUserId()));
        String result = HttpPost("/iotsite/check/get_check_list_plus", options, "application/x-www-form-urlencoded");
        HashMap<GroupBean, List<ProjectCheckBean>> checkResult = new HashMap<>();
        if (result != null) {
            JSONArray data = JSON.parseObject(result).getJSONArray("data");

            for (int i = 0; i < data.size(); i++) {
                int groupId = ((JSONObject) data.get(i)).getIntValue("id");
                //String groupName = ((JSONObject) data.get(i)).get("name").toString();
                boolean isLeader = ((JSONObject) data.get(i)).getBooleanValue("isLeader");
                List<ProjectCheckBean> projectCheckBeans = JSON.parseArray(((JSONObject) data.get(i)).getString("checkList"), ProjectCheckBean.class);
                checkResult.put(new GroupBean(groupId, "555", isLeader ? 1 : 0), projectCheckBeans);
            }
        }
        return checkResult;
    }

    public static List<ClientBean> shelfProjects_Get(@Nullable HashMap<String, String> options) {
        String result = HttpGet("/iotsite/contract/contract_all", options, null);
        if (result != null) {
            return JSON.parseArray(JSON.parseObject(result).getString("data"), ClientBean.class);
        } else return new ArrayList<>();
    }

    public static boolean login(@Nullable HashMap<String, String> options) {
        String result = HttpPost("/iotsite/user/login", options, "application/x-www-form-urlencoded");
        if (result != null) {
            JSONObject data = JSON.parseObject(result).getJSONObject("data");
            UserUtil.saveUserId(data.getIntValue("id"));
            UserUtil.saveLoggedInAccount(options.get("account"));
            /*user.setPassword(options.get("password"));
            UserUtil.setUser(user);*/
            return true;
        } else return false;
    }

    /**
     * 上传检查结果
     * id	指明更新的检查项目ID
     * projectId	填充内容
     * groupId	填充内容
     * userId	上传者的用户id
     * checkSystemId	填充内容
     * grade	打分
     * description	描述检查结果
     */
    public static boolean uploadCheckPost(@Nullable HashMap<String, String> options) {
        String result = HttpPost("/iotsite/check/upload_result", options, "application/x-www-form-urlencoded");
        return result == null ? false : true;
    }

    /**
     * 上传审核检查结果
     * checkId	审核特定的检查结果
     * flag	审核结果：1为通过；2为未通过
     */
    public static boolean reviewCheckPost(@Nullable HashMap<String, String> options) {
        String result = HttpPost("/iotsite/check/review_result", options, "application/x-www-form-urlencoded");
        return result == null ? false : true;
    }

    public static UserBean getUser() {
        int id = UserUtil.getUserId();
        if (id != -1) {
            String result = HttpPost("/iotsite/user/index/" + id, null, "application/x-www-form-urlencoded");
            if (result != null) {
                return JSON.parseObject(JSON.parseObject(result).getString("data"), UserBean.class);
            }
        }
        return new UserBean();
    }
}

