package com.graduation.smart_site_inspection_system.Bean.ProjectTree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.TreeItem;
import com.baozi.treerecyclerview.item.TreeItemGroup;
import com.graduation.smart_site_inspection_system.R;

import java.util.List;

public class CheckSys1Item extends TreeItemGroup<ClientBean.ProjectBean.CheckSys1Bean> {
    @Nullable
    @Override
    protected List<TreeItem> initChild(ClientBean.ProjectBean.CheckSys1Bean data) {
        List<TreeItem> items = ItemHelperFactory.createItems(data.subCheckSystems, this);
        return items;
    }

    @Override
    public int getLayoutId() {
        return R.layout.check_sys1_item;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder) {
        viewHolder.setText(R.id.tv_content, data.name);
    }
}
