package com.graduation.smart_site_inspection_system.Fragment_ProjectCheck;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.graduation.smart_site_inspection_system.Bean.GroupBean;
import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.projectCheckGet;
import com.graduation.smart_site_inspection_system.util.reviewCheckPost;
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

//    Dialog控件
    private TextView riskLevel;
    private TextView finishDateTime;
    private TextView checkSystemId;
    private TextView description;

    private ConstraintLayout test;
    private int account;  //用户账户，通过偏好设置获取
    private boolean isLeader=false;  //是否是组长
    private int groupPosition=0;  //当前组下标

    private HashMap<GroupBean, List<ProjectCheckBean>> data;  //handler返回的数据
    private List<GroupBean> groupBeanList;
    private List<String> groupName=new ArrayList<>();
    private ArrayList<ArrayList<ProjectCheckBean>> projectCheckBeanLists=new ArrayList<>();
    private ArrayAdapter groupA;
    private String[] riskLevelStr = new String[]{"轻度风险","一般风险","高危风险"};


    private final Handler mHandler=new Handler(){
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
                    for(GroupBean g : groupBeanList){
                        groupName.add(String.valueOf(g.getName()));
                    }
                    if(getContext()!=null){
                        groupA = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,groupName);
                        groupA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mGroupLv.setAdapter(groupA);
                    }
                    break;
                case reviewCheckPost.Msg_reviewCheckPost_what: //审核检查结果
                    Toast.makeText(getContext(), "审核成功！", Toast.LENGTH_SHORT).show();
                    break;
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
                groupPosition = position;
//                判断是否是组长
                isLeader = groupBeanList.get(position).getIsLeader()==1;
//                适配项目列表
                if(projectCheckBeanLists!=null && projectCheckBeanLists.size()!=0){
                    mAdapter = new ProjectCheckBaseAdapter(getActivity(),projectCheckBeanLists.get(position));
                    mListView.setAdapter(mAdapter);
                    getView().findViewById(R.id.tv_noData).setVisibility(View.GONE);
                }else getView().findViewById(R.id.tv_noData).setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long l) {
                final ProjectCheckBean nowClickPCB = projectCheckBeanLists.get(groupPosition).get(position);
//                组长审核
                if(isLeader){
                    AlertDialog normalDialog = new AlertDialog.Builder(getContext())
                            .setView(R.layout.leader_check)
                            .setTitle("项目审核")
                            .setPositiveButton("通过", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reviewCheckPost mCPost = new reviewCheckPost(
                                            String.valueOf(nowClickPCB.getId())
                                            ,"1"
                                            ,mHandler
                                    );
                                    mCPost.start();
                                }
                            })
                            .setNegativeButton("不予通过", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    reviewCheckPost mCPost = new reviewCheckPost(
                                            String.valueOf(nowClickPCB.getId())
                                            ,"0"
                                            ,mHandler
                                    );
                                    mCPost.start();
                                }
                            })
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    normalDialog.show();
                    normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    normalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                    normalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.BLACK);

                    Window window = normalDialog.getWindow();
                    riskLevel = (TextView) window.findViewById(R.id.leader_riskLevel);
                    finishDateTime = (TextView) window.findViewById(R.id.leader_finishDateTime);
                    checkSystemId = (TextView) window.findViewById(R.id.leader_checkSystemId);
                    description = (TextView) window.findViewById(R.id.leader_description);

//                    0~2为正常风险值
                    int nowLevel = nowClickPCB.getGrade();
                    riskLevel.setText((nowLevel>=0 && nowLevel<=2) ? riskLevelStr[nowLevel+1] : "服务器异常");

                    finishDateTime.setText(nowClickPCB.getFinishDateTime());
                    checkSystemId.setText(String.valueOf(nowClickPCB.getChecksys_id()));
                    description.setText(nowClickPCB.getDescription());
                }else{
//                组员不跳转
                    if(nowClickPCB.getExamState()==0){
//                        Intent startSubmit = new Intent(getContext(), SubmitActivity.class);
//                        startSubmit.putExtra("sys2Id", nowClickPCB.getId());
//                        startSubmit.putExtra("sys2Name", "重新提交");
//                        startSubmit.putExtra("projectId", nowClickPCB.getProjectId());
//                        startSubmit.putExtra("projectName", "后端没给我项目Name");
//                        startSubmit.putExtra("clientName", "后端没给我委托方Name");
//                        getContext().startActivity(startSubmit);
                    }else{
                        Toast.makeText(getContext(), "项目还在审核中！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initData(){
        mListView=(ListView) getActivity().findViewById(R.id.check_lv);
        mGroupLv=(Spinner) getActivity().findViewById(R.id.check_groupLv);
//        偏好设置获取用户id
        account = getActivity().getSharedPreferences("mine",getActivity().MODE_PRIVATE).getInt("id", 0);

//        网络子线程，根据account，获取数据
        projectCheckGet mpcGet = new projectCheckGet(mHandler,Integer.toString(account));
        mpcGet.start();
    }
}
