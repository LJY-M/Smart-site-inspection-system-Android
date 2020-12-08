package com.graduation.smart_site_inspection_system.views;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.graduation.smart_site_inspection_system.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 传入委托方、项目、体系、项目id等参数，提交时一并提交
 */
public class SubmitActivity extends AppCompatActivity {
    private Spinner clientName;  //委托方
    private Spinner projectName;  //项目
    private Spinner groupName;  //体系
    private Spinner risk;  //风险等级

    private EditText checkPartName;  //检查部位
    private EditText problem;  //问题描述

    private ImageView photo;  //问题拍照
    private ImageView submit;  //提交
    private ImageView back;  //返回

    private String cN = "委托方";  //委托方
    private String pN = "项目";  //项目
    private String gN = "体系";  //体系
    private String rN = "风险等级";  //风险等级

    private List<String> riskStr = new ArrayList<>();
//    假数据
    private List<String> clientStr = new ArrayList<>();
    private List<String> projectStr = new ArrayList<>();
    private List<String> groupStr = new ArrayList<>();

    private ArrayAdapter clientStrA;
    private ArrayAdapter projectStrA;
    private ArrayAdapter groupStrA;
    private ArrayAdapter riskStrA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉顶部标题栏
        setContentView(R.layout.activity_submit);

        init();  //控件初始化

//        委托方监听事件
        clientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cN = clientStr.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        项目监听事件
        projectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pN = projectStr.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        体系监听事件
        groupName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gN = groupStr.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        风险等级监听事件
        risk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rN = riskStr.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        提交监听
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubmitActivity.this
                        ,cN+","+pN+","+gN+","+rN+","+checkPartName.getText()+","+problem.getText()
                        , Toast.LENGTH_LONG).show();
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
        clientName = (Spinner)findViewById(R.id.submit_clientname);
        projectName = (Spinner)findViewById(R.id.submit_projectname);
        groupName = (Spinner)findViewById(R.id.submit_groupname);
        risk = (Spinner)findViewById(R.id.submit_risk);
        checkPartName = (EditText)findViewById(R.id.submit_checkpartname);
        problem = (EditText)findViewById(R.id.submit_problem);
        photo = (ImageView)findViewById(R.id.submit_photo);
        submit = (ImageView)findViewById(R.id.submit_btn);
        back = (ImageView)findViewById(R.id.submit_back);

//        假数据
        riskStr.add("轻度风险");
        riskStr.add("一般风险");
        riskStr.add("高危风险");
        clientStr.add("委托方甲");
        clientStr.add("委托方乙");
        clientStr.add("委托方丙");
        projectStr.add("项目1");
        projectStr.add("项目2");
        projectStr.add("项目3");
        groupStr.add("体系a");
        groupStr.add("体系b");
        groupStr.add("体系c");

//        为下拉列表定义一个数组适配器
        clientStrA = new ArrayAdapter(this,android.R.layout.simple_list_item_1,clientStr);
        projectStrA = new ArrayAdapter(this,android.R.layout.simple_list_item_1,projectStr);
        groupStrA = new ArrayAdapter(this,android.R.layout.simple_list_item_1,groupStr);
        riskStrA = new ArrayAdapter(this,android.R.layout.simple_list_item_1,riskStr);
//        为适配器设置下拉菜单的样式
        clientStrA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStrA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupStrA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        riskStrA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        将适配器配置到下拉列表上
        clientName.setAdapter(clientStrA);
        projectName.setAdapter(projectStrA);
        groupName.setAdapter(groupStrA);
        risk.setAdapter(riskStrA);
    }
}