package com.graduation.smart_site_inspection_system.Fragment_ProjectCheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.smart_site_inspection_system.Bean.ProjectCheckBean;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.GetImageAsyncTask;

import java.util.ArrayList;

public class ProjectCheckBaseAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<ProjectCheckBean> pcBeans;

    public ProjectCheckBaseAdapter(Context context, ArrayList<ProjectCheckBean> pcBeans){
        layoutInflater=LayoutInflater.from(context);
        this.context=context;
        this.pcBeans=pcBeans;
    }


    //获取数据长度
    @Override
    public int getCount() {
        return pcBeans.size();
    }
    //    获取当前子项对象
    @Override
    public Object getItem(int position) {
        return pcBeans.get(position);
    }
    //    取当前子项ID
    @Override
    public long getItemId(int position) {
        return position;
    }

    //    容器
    class ViewHolder{
        ImageView im;
        TextView time;
        TextView project;
        TextView status;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.listview_check,null);

            viewHolder.im = convertView.findViewById(R.id.check_lv_im);
            viewHolder.time = convertView.findViewById(R.id.check_lv_time);
            viewHolder.project = convertView.findViewById(R.id.check_lv_project);
            viewHolder.status = convertView.findViewById(R.id.check_lv_status);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

//        获取图片
//        GetImageAsyncTask getImage = new GetImageAsyncTask(viewHolder.im);
//        getImage.execute(pcBeans.get(i).getIm_url());
        viewHolder.time.setText(pcBeans.get(i).getCreatetime());
        viewHolder.project.setText(pcBeans.get(i).getDescription());
//        服务器传过来的要么是未审核的要么是未通过的，exam==1就是未审核，exam==0就是未检查或未通过
        viewHolder.status.setText(pcBeans.get(i).getExamState()==1 ? "未审核" : "未通过");
        return convertView;
    }

}
