package com.graduation.smart_site_inspection_system.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用法示例：
 *      GetImageAsyncTask getImage = new GetImageAsyncTask(你的ImageView控件);
 *      getImage.execute(图片的url);
 * 注意：
 *      需要在UI线程内使用
 */
public class GetImageAsyncTask extends AsyncTask<String, String, Bitmap> {
    private ImageView iv;
    //服务器地址
    private static final String mTomcatImage = "http://39.99.249.23:8080/pictures";
    //构造器，传入需要填充的ImageView
    public GetImageAsyncTask(ImageView iv) {
        this.iv = iv;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(mTomcatImage + strings[0]);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //初始化连接对象
            conn.setRequestMethod("GET");
            //连接和读取超时
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            //与服务器建立连接
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                //读取流里的数据，构建成bitmap位图
                Bitmap bm = BitmapFactory.decodeStream(is);
                return bm;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        //结束后更新UI
        if(bitmap!=null){
            iv.setImageBitmap(bitmap);
        }
    }
}


