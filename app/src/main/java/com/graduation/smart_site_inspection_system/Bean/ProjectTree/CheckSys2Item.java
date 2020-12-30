package com.graduation.smart_site_inspection_system.Bean.ProjectTree;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.views.SubmitActivity;

public class CheckSys2Item extends TreeItem<ClientBean.ProjectBean.CheckSys1Bean.CheckSys2Bean> {
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_content, data.name);
        if(!data.examState)
        viewHolder.setBackgroundRes(R.id.tv_content,R.drawable.border);
        else if (data.passState)
            viewHolder.setBackgroundRes(R.id.tv_content,R.drawable.border_passed);
        else viewHolder.setBackgroundRes(R.id.tv_content,R.drawable.border_examing);
    }

    @Override
    public int getLayoutId() {
        return R.layout.check_sys2_item;
    }

    @Override
    public int getSpanSize(int maxSpan) {
        return 1;
    }

    @Override
    public void onClick(ViewHolder viewHolder) {
        super.onClick(viewHolder);
        //Toast.makeText(viewHolder.itemView.getContext(), viewHolder.getTextView(R.id.tv_content).getText(), Toast.LENGTH_SHORT).show();
        if(!data.examState&&!data.passState) {
            Intent startSubmit = new Intent(viewHolder.itemView.getContext(), SubmitActivity.class);
            startSubmit.putExtra("sys2Id", this.data.id);
            startSubmit.putExtra("sys2Name", this.data.name);
            CheckSys1Item checkSys1Item = (CheckSys1Item) getParentItem();
            if (checkSys1Item != null) {
                ProjectItem projectItem = (ProjectItem) checkSys1Item.getParentItem();
                if (projectItem != null) {
                    startSubmit.putExtra("projectId", projectItem.getData().projectId);
                    startSubmit.putExtra("projectName", projectItem.getData().projectName);
                    ClientItem clientItem = (ClientItem) projectItem.getParentItem();
                    if (clientItem != null) {
                        startSubmit.putExtra("clientName", clientItem.getData().clientName);
                    }
                }

            }
            viewHolder.itemView.getContext().startActivity(startSubmit);
        }
    }
}
