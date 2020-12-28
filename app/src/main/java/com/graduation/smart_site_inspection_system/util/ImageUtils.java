package com.graduation.smart_site_inspection_system.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] getScaledBitArray() throws IOException {
        FileInputStream fis=new FileInputStream(getFaceImagePath(MyApplication.getContext()).getAbsolutePath());
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream scaledBitmap = new ByteArrayOutputStream();
        int options_ = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options_, scaledBitmap);
        int nowLength = scaledBitmap.toByteArray().length;
        while (nowLength > 2*1024*1024) {//循环判断如果压缩后图片是否大于2M
            scaledBitmap.reset();
            options_ = Math.max(50, options_ - 10);
            System.out.println(options_);
            bitmap.compress(Bitmap.CompressFormat.JPEG, options_, scaledBitmap);
            nowLength = scaledBitmap.toByteArray().length;
            if (options_ == 50)
                break;
        }
        fis.close();
        return scaledBitmap.toByteArray();
    }

    private static File getFaceImagePath(Context context){
        return new File(context.getExternalCacheDir(), "image.jpg");
    }

    public static Uri getFaceImageUri(Context context) {
        return FileProvider.getUriForFile(context,
                "com.graduation.smart_site_inspection_system.fileprovider",
                getFaceImagePath(context));
//        return Uri.fromFile(mPhotoFile);
    }
}
