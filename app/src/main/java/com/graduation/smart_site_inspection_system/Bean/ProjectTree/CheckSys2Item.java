package com.graduation.smart_site_inspection_system.Bean.ProjectTree;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.TreeItem;
import com.graduation.smart_site_inspection_system.R;

public class CheckSys2Item extends TreeItem<ClientBean.ProjectBean.CheckSys1Bean.CheckSys2Bean> {
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_content,data.name);
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
        Toast.makeText(viewHolder.itemView.getContext(), viewHolder.getTextView(R.id.tv_content).getText(), Toast.LENGTH_SHORT).show();
    }
}
