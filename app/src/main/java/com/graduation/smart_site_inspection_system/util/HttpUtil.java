package com.graduation.smart_site_inspection_system.util;

import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectTree.ClientBean;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    //    服务器地址
    private static final String SERVER = "http://39.106.66.219:3000/mock/11/";//"http://39.99.249.23:8080/Taobao/";

    //http操作
    private static String HttpPost(String subUrl, @Nullable HashMap<String, String> options) {
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
                byte buffer[] = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    message.write(buffer, 0, len);
                }
                is.close();
                message.close();
                result = new String(message.toByteArray());
            } else {
                Toast.makeText(MyApplication.getContext(), conn.getResponseMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String HttpGet(String subUrl, @Nullable HashMap<String, String> options) {
        String address = SERVER + subUrl;
        String message = null;
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestProperty("Authorization", UserUtil.getToken());
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                byte[] data = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int length = 0;
                while ((length = inputStream.read(data)) != -1) {
                    String s = new String(data);
                    sb.append(s);
                }
                message = sb.toString();
                inputStream.close();
            }else {
                Toast.makeText(MyApplication.getContext(), connection.getResponseMessage(), Toast.LENGTH_SHORT).show();
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static HashMap<GroupBean, List<ProjectCheckBean>> projectCheck_Get(@Nullable HashMap<String, String> options) {
        String result = HttpPost("projectCheck_Get.do", options);
        if (result != null) {
            JSONArray data = JSON.parseObject(result).getJSONArray("data");
            HashMap<GroupBean, List<ProjectCheckBean>> checkResult = new HashMap<>();
            for (int i = 0; i < data.size(); i++) {
                int groupId = ((JSONObject) data.get(i)).getIntValue("id");
                String groupName = ((JSONObject) data.get(i)).get("name").toString();
                boolean isLeader = ((JSONObject) data.get(i)).getBooleanValue("isLeader");
                List<ProjectCheckBean> projectCheckBeans = JSON.parseArray(((JSONObject) data.get(i)).getJSONArray("projectCheckResult").toJSONString(), ProjectCheckBean.class);
                checkResult.put(new GroupBean(groupId, groupName, isLeader), projectCheckBeans);
            }
            return checkResult;
        }
        return null;
    }

    public static List<ClientBean> shelfProjects_Get(@Nullable HashMap<String, String> options) {
        String result = HttpGet("iotsite/contract/contract_all", options);
        if (result != null) {
            JSONArray data = JSON.parseObject(result).getJSONArray("data");
            return JSON.parseArray(data.toJSONString(), ClientBean.class);
        } else return null;
    }
}

