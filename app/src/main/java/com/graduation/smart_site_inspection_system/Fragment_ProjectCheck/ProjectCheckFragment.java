package com.graduation.smart_site_inspection_system.Fragment_ProjectCheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.projectCheckGet;
import com.graduation.smart_site_inspection_system.views.SubmitActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 检查页面，根据用户id，即account，获取数据
 * 1、如果是组长，服务器返回的是所有待审核条目，点击后弹出dialog进行审核
 * 2、如果是组员，返回的是自己的待审核条目和未通过条目，点击未通过条目可以进入提交页再次提交
 */
public class ProjectCheckFragment extends Fragment {

    private ListView mListView;
    private Spinner mGroupLv;
    private ProjectCheckBaseAdapter mAdapter;

    private Handler handler;
    private ConstraintLayout test;
    private int account;  //用户账户，通过偏好设置获取
    private boolean isGroup;  //是否是组长

    private ArrayList<ProjectCheckBean> pcBeans;
    private ProjectCheckBean nowPCBean;

    private HashMap<GroupBean, List<ProjectCheckBean>> data;  //handler返回的数据
    private List<GroupBean> groupBeanList;
    private List<String> groupName=new ArrayList<>();
    private ArrayList<ArrayList<ProjectCheckBean>> projectCheckBeanLists=new ArrayList<>();
    private ArrayAdapter groupA;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case projectCheckGet.Msg_projectCheckGet_what: //根据用户id得到项目列表
                    data=(HashMap<GroupBean, List<ProjectCheckBean>>)msg.obj;
                    groupBeanList=new ArrayList<>(data.keySet());
                    for(GroupBean groupBean:groupBeanList){
                        projectCheckBeanLists.add((ArrayList<ProjectCheckBean>) data.get(groupBean));
                    }
// TODO                   此处解析data数据
                    for(GroupBean g : groupBeanList){
                        groupName.add(String.valueOf(g.getId()));
                    }
                    groupA = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,groupName);
                    groupA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mGroupLv.setAdapter(groupA);
            }
        }};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projectcheck, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        initData();
        mGroupLv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    适配项目列表
                if(projectCheckBeanLists!=null && projectCheckBeanLists.size()!=0){
                    mAdapter = new ProjectCheckBaseAdapter(getActivity(),projectCheckBeanLists.get(position));
                    mListView.setAdapter(mAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData(){
        mListView=(ListView) getActivity().findViewById(R.id.check_lv);
        mGroupLv=(Spinner) getActivity().findViewById(R.id.check_groupLv);
//        偏好设置获取用户id
        account = getActivity().getSharedPreferences("mine",getActivity().MODE_PRIVATE).getInt("id", 0);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
//        网络子线程，根据account，获取数据
        projectCheckGet mpcGet = new projectCheckGet(mHandler,Integer.toString(account));
        mpcGet.start();
    }
}
