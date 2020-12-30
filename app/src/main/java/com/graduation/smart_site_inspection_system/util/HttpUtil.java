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

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
    @org.jetbrains.annotations.Nullable
    private static String HttpPost(String subUrl, @Nullable HashMap<String, String> options, @Nullable String content_type) {
        String address = SERVER + subUrl;
        String result = null;
        try {
            URL url = new URL(address);//初始化URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");//请求方式
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Authorization", UserUtil.getToken());
            if (content_type != null && !content_type.isEmpty())
                connection.setRequestProperty("Content-type", content_type);
            connection.connect();
            OutputStream out = connection.getOutputStream();
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
                if (JSON.parseObject(result).getIntValue("code") == 200) {
                    String token = connection.getHeaderField("Authorization");
                    if (token != null && !token.isEmpty())
                        UserUtil.saveToken(token);
                } else if (JSON.parseObject(result).getIntValue("code") == 2008) {
                    MyApplication.reLogin();
                }else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @org.jetbrains.annotations.Nullable
    private static String HttpGet(String subUrl, @Nullable HashMap<String, String> options, @Nullable String content_type) {
        StringBuilder address = new StringBuilder(SERVER + subUrl);
        String result = null;
        try {
            if (options != null) {
                int count = 0;
                for (Map.Entry<String, String> option : options.entrySet()) {
                    String data = "";
                    if (++count > 1)
                        data += "&";
                    else
                        address.append("?");
                    data += (option.getKey() + "=" + option.getValue());
                    address.append(data);
                }
            }
            URL url = new URL(address.toString());
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
                if (JSON.parseObject(result).getIntValue("code") == 200) {
                    String token = connection.getHeaderField("Authorization");
                    if (token != null && !token.isEmpty())
                        UserUtil.saveToken(token);
                } else if (JSON.parseObject(result).getIntValue("code") == 2008) {
                    MyApplication.reLogin();
                }else return null;
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @org.jetbrains.annotations.Nullable
    private static String HttpPut(String subUrl, @Nullable HashMap<String, String> options, @Nullable String content_type) {
        String address = SERVER + subUrl;
        String result = null;
        try {
            URL url = new URL(address);//初始化URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");//请求方式
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Authorization", UserUtil.getToken());
            if (content_type != null && !content_type.isEmpty())
                connection.setRequestProperty("Content-type", content_type);
            connection.connect();
            OutputStream out = connection.getOutputStream();
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
                if (JSON.parseObject(result).getIntValue("code") == 200) {
                    String token = connection.getHeaderField("Authorization");
                    if (token != null && !token.isEmpty())
                        UserUtil.saveToken(token);
                } else if (JSON.parseObject(result).getIntValue("code") == 2008) {
                    MyApplication.reLogin();
                }else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @org.jetbrains.annotations.Nullable
    private static String HttpPicturePost(String subUrl, byte[] image, @Nullable HashMap<String, String> options) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String address = SERVER + subUrl;
        String result = null;
        try {
            URL url = new URL(address);//初始化URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");//请求方式
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Authorization", UserUtil.getToken());
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.connect();

            DataOutputStream ds = new DataOutputStream(connection.getOutputStream());
            //======传递参数
/*            if (options != null) {
                for (Map.Entry<String, String> option : options.entrySet()) {
                    ds.writeBytes(boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"" + option.getKey() + "\"" + end);
                    ds.writeBytes(end);
                    ds.writeBytes(option.getValue() + end);
                }
            }*/
            ds.writeBytes(twoHyphens+boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"" + "projectId" + "\"" + end);
            ds.writeBytes(end);
            ds.writeBytes(options.get("projectId") + end);

            ds.writeBytes(twoHyphens+boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"" + "checkSystemId" + "\"" + end);
            ds.writeBytes(end);
            ds.writeBytes(options.get("checkSystemId") + end);
            //======传递文件======
            ds.writeBytes(twoHyphens+boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"image.jpg\"" + end);
            ds.writeBytes("Content-Type: image/jpeg" + end);
            ds.writeBytes(end);
            ds.write(image);
            ds.writeBytes(end);


            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            ds.flush();
            ds.close();
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
                if (JSON.parseObject(result).getIntValue("code") == 200) {
                    String token = connection.getHeaderField("Authorization");
                    if (token != null && !token.isEmpty())
                        UserUtil.saveToken(token);
                } else if (JSON.parseObject(result).getIntValue("code") == 2008) {
                    MyApplication.reLogin();
                }else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @NotNull
    public static HashMap<GroupBean, List<ProjectCheckBean>> projectCheck_Get() {
        HashMap<String, String> options = new HashMap<>();
        options.put("userId", String.valueOf(UserUtil.getUserId()));
        String result = HttpGet("/iotsite/check/get_check_list_plus", options, "application/x-www-form-urlencoded");
        HashMap<GroupBean, List<ProjectCheckBean>> checkResult = new HashMap<>();
        if (result != null) {
            JSONArray data = JSON.parseObject(result).getJSONArray("data");

            for (int i = 0; i < data.size(); i++) {
/*                int groupId = ((JSONObject) data.get(i)).getIntValue("id");
                //String groupName = ((JSONObject) data.get(i)).get("name").toString();
                boolean isLeader = ((JSONObject) data.get(i)).getBooleanValue("isLeader");*/
                GroupBean groupBean = JSON.parseObject(((JSONObject) data.get(i)).getString("userGroup"), GroupBean.class);
                JSONObject group;
                if ((group = ((JSONObject) data.get(i)).getJSONObject("group")) != null) {
                    groupBean.setName(group.getString("name"));
                } else groupBean.setName("错误的组名");
                JSONArray checkResultList = ((JSONObject) data.get(i)).getJSONArray("checkResultList");
                List<ProjectCheckBean> projectCheckBeans = new ArrayList<>();
                for (int j = 0; j < checkResultList.size(); j++) {
                    ProjectCheckBean projectCheckBean = JSON.parseObject(
                            ((JSONObject) checkResultList.get(j)).getString("check"),
                            ProjectCheckBean.class);
                    projectCheckBean.setPictureList(JSON.parseArray(
                            ((JSONObject) checkResultList.get(j)).getString("pictureList"),
                            String.class));
                    projectCheckBeans.add(projectCheckBean);
                }
                checkResult.put(groupBean, projectCheckBeans);
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
     * //id	指明更新的检查项目ID
     * projectId	填充内容
     * //groupId	填充内容
     * userId	上传者的用户id
     * checkSystemId	填充内容
     * grade	打分
     * description	描述检查结果
     */
    public static boolean uploadCheckPost(@Nullable HashMap<String, String> options) {
        String result = HttpPut("/iotsite/check/check", options, "application/x-www-form-urlencoded");
        return (result!=null && JSON.parseObject(result).getIntValue("code")==200) ? true : false;
    }

    /**
     * 上传审核检查结果
     * checkId	审核特定的检查结果
     * flag	审核结果：1为通过；2为未通过
     */
    public static boolean reviewCheckPost(@Nullable HashMap<String, String> options) {
        String result = HttpPut("/iotsite/check/review_result", options, "application/x-www-form-urlencoded");
        return (result!=null && JSON.parseObject(result).getIntValue("code")==200) ? true : false;
    }

    /**
     * 上传图片
     * 传入项目id和检查体系id
     * file	文件	上传图片
     */
    public static boolean uploadPicturePost(byte[] image, @Nullable HashMap<String, String> options) {
        String result = HttpPicturePost("/iotsite/check/upload_picture", image, options);
        return (result!=null && JSON.parseObject(result).getIntValue("code")==200) ? true : false;
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

