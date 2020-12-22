package com.graduation.smart_site_inspection_system.Bean.ProjectTree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.graduation.smart_site_inspection_system.R;

import java.util.List;

public class ProjectItem extends TreeItemGroup<ClientBean.ProjectBean> {
    @Nullable
    @Override
    protected List<TreeItem> initChild(ClientBean.ProjectBean data) {
        List<TreeItem> items = ItemHelperFactory.createItems(data.checkSystems, this);
        return items;
    }

    @Override
    public int getLayoutId() {
        return R.layout.project_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_content, data.projectName);
    }
}
