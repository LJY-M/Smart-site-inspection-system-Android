package com.graduation.smart_site_inspection_system.Fragment_My;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.Bean.UserBean;
import com.graduation.smart_site_inspection_system.Fragment_ProjectCheck.ProjectCheckBaseAdapter;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.HttpUtil;
import com.graduation.smart_site_inspection_system.util.UserUtil;
import com.graduation.smart_site_inspection_system.util.getUserPost;
import com.graduation.smart_site_inspection_system.util.projectCheckGet;
import com.graduation.smart_site_inspection_system.views.MyDetailActivity;
import com.graduation.smart_site_inspection_system.views.SubmitActivity;
import com.graduation.smart_site_inspection_system.views.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFragment extends Fragment {
    private Handler handler;
    private UserBean mUser;

    private TextView mLoginTv;
    private TextView mQuit;
    private ConstraintLayout mDetail;

    public MyFragment(Handler handler) {
        this.handler = handler;
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case getUserPost.Msg_getUserPost_what: //获取用户bean
                    mUser=(UserBean)msg.obj;
                    mLoginTv.setText(mUser.getName()==null ? "无法连接到网络..." : (mUser.getName()+"\n欢迎您使用本系统！"));
                    break;
            }
        }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();

//        退出登录
        mQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserUtil.logout();
                Intent toLogin = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(toLogin);
                getActivity().finish();
            }
        });

//        个人信息
        mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUser!=null){
                    Bundle mUBB = new Bundle();
                    mUBB.putString("account",String.valueOf(mUser.getAccount()));
                    mUBB.putString("name", mUser.getName()==null ? "无信息" : mUser.getName());
                    mUBB.putString("telephone",mUser.getTelephone()==null ? "无信息" : mUser.getTelephone());
                    mUBB.putString("sex", mUser.getSex()==null ? "无信息" : mUser.getSex());
                    mUBB.putString("address", mUser.getAddress()==null ? "无信息" : mUser.getAddress());
//                mUBB.put
                    Intent toDetail = new Intent(getContext(), MyDetailActivity.class);
                    toDetail.putExtra("mUser", mUBB);
                    getContext().startActivity(toDetail);
                }
            }
        });
    }

    private void initData(){
        mLoginTv = (TextView)getActivity().findViewById(R.id.my_login);
        mQuit = (TextView)getActivity().findViewById(R.id.my_quit);
        mDetail = (ConstraintLayout)getActivity().findViewById(R.id.btn_my_detail);

        getUserPost mGetUser = new getUserPost(mHandler);
        mGetUser.start();
    }
}
