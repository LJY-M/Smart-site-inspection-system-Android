package com.graduation.smart_site_inspection_system.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.ImageUtils;
import com.graduation.smart_site_inspection_system.util.UserUtil;
import com.graduation.smart_site_inspection_system.util.uploadCheckPost;
import com.graduation.smart_site_inspection_system.util.uploadPicturePost;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 传入委托方、项目、体系、项目id等参数，提交时一并提交
 */
public class SubmitActivity extends AppCompatActivity {
    private TextView clientName;  //委托方
    private TextView projectName;  //项目
    private TextView groupName;  //体系
    private Spinner risk;  //风险等级

    private EditText checkPartName;  //检查部位
    private EditText problem;  //问题描述

    private ImageView photo;  //问题拍照
    private ImageView submit;  //提交
    private ImageView back;  //返回

    private String grade = "1";  //风险等级

    private List<String> riskStr = new ArrayList<>();
    private ArrayAdapter riskStrA;

//    图片
    private final int TAKE_PHOTO_REQUEST_CODE=101;
    private byte[] b;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case uploadCheckPost.Msg_uploadCheckPost_what: //上传检查结果
                    submit.setImageResource(R.drawable.submit_success);
                    Toast.makeText(SubmitActivity.this, "信息提交成功！",Toast.LENGTH_LONG).show();
                    break;
                case uploadPicturePost.Msg_uploadPicturePost_what:
                    Toast.makeText(SubmitActivity.this, "图片提交成功！",Toast.LENGTH_LONG).show();
                    break;
            }
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉顶部标题栏
        setContentView(R.layout.activity_submit);

        init();  //控件初始化

//        风险等级监听事件
        risk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = position+1+"";
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        图片拍照上传
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });


//        提交监听
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                上传审核结果
                uploadCheckPost mpcGet = new uploadCheckPost(
                        String.valueOf(getIntent().getIntExtra("projectId",0))
                        ,String.valueOf(UserUtil.getUserId())
                        ,String.valueOf(getIntent().getIntExtra("sys2Id",0))
                        ,grade
                        ,checkPartName.getText().toString()+":"+problem.getText().toString()
                        ,mHandler);
                mpcGet.start();

//                上传图片
                if(b!=null) {
                    uploadPicturePost mPicture = new uploadPicturePost(
                            String.valueOf(getIntent().getIntExtra("projectId", 0))
                            //将图片转成字符串，避免乱码
                            , String.valueOf(getIntent().getIntExtra("sys2Id", 0))
                            , Base64.encodeToString(b, Base64.DEFAULT)
                            , mHandler
                    );

                    mPicture.start();
                }
            }
        });

//        返回监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        clientName = (TextView) findViewById(R.id.my_detail_account);
        projectName = (TextView) findViewById(R.id.my_detail_name);
        groupName = (TextView) findViewById(R.id.my_detail_telephone);
        risk = (Spinner)findViewById(R.id.submit_risk);
        checkPartName = (EditText)findViewById(R.id.submit_checkpartname);
        problem = (EditText)findViewById(R.id.submit_problem);
        photo = (ImageView)findViewById(R.id.submit_photo);
        submit = (ImageView)findViewById(R.id.submit_btn);
        back = (ImageView)findViewById(R.id.my_detail_back);

//        真数据
        riskStr.add("轻度风险");
        riskStr.add("一般风险");
        riskStr.add("高危风险");
        clientName.setText(getIntent().getStringExtra("clientName"));
        projectName.setText(getIntent().getStringExtra("projectName"));
        groupName.setText(getIntent().getStringExtra("sys2Name"));

//        为下拉列表定义一个数组适配器
        riskStrA = new ArrayAdapter(this,android.R.layout.simple_list_item_1,riskStr);
//        为适配器设置下拉菜单的样式
        riskStrA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        将适配器配置到下拉列表上
        risk.setAdapter(riskStrA);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = ImageUtils.getFaceImageUri(getApplicationContext());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    try {
                        b = ImageUtils.getScaledBitArray();
                        photo.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.storage_permission_error),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}