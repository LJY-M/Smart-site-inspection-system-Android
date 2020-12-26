package com.graduation.smart_site_inspection_system.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.graduation.smart_site_inspection_system.util.UserUtil;
import com.graduation.smart_site_inspection_system.util.uploadCheckPost;

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

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case uploadCheckPost.Msg_uploadCheckPost_what: //上传检查结果
                    submit.setImageResource(R.drawable.submit_success);
                    Toast.makeText(SubmitActivity.this, "提交成功！",Toast.LENGTH_LONG).show();
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
                grade = riskStr.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        提交监听
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCheckPost mpcGet = new uploadCheckPost("3",
                        String.valueOf(getIntent().getIntExtra("projectId",0)),
                        "111",
                        String.valueOf(UserUtil.getUserId()),
                        String.valueOf(getIntent().getIntExtra("sys2Id",0)),
                        grade,
                        checkPartName.getText().toString()+":"+problem.getText().toString(),
                        mHandler);
                mpcGet.start();
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
}