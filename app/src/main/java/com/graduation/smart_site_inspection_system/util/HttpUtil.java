package com.graduation.smart_site_inspection_system.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    //    服务器地址
    private static final String mTomcat = "http://39.99.249.23:8080/Taobao/";

    //http操作
    private static JSONObject HttpPost(HashMap<String,String> options, String subUrl) {
        String address = mTomcat + subUrl;
        JSONObject result = null;
        try {
            URL url = new URL(address);//初始化URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//请求方式
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            for(Map.Entry<String,String> option:options.entrySet()){
                conn.setRequestProperty(option.getKey(),option.getValue());
            }
            conn.connect();
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
                result = new JSONObject(new String(message.toByteArray()));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject projectCheck_Get(HashMap<String,String> options) {
        return HttpPost(options,"projectCheck_Get.do");
    }

    public  static JSONObject shelfProjects_Get(HashMap<String,String> options) {
        return HttpPost(options,"shelfProjects.do");
    }
}

