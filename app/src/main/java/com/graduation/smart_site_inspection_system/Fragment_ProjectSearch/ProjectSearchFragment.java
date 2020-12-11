package com.graduation.smart_site_inspection_system.Fragment_ProjectSearch;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.item.SimpleTreeItem;
import com.graduation.smart_site_inspection_system.R;


public class ProjectSearchFragment extends Fragment {
    private Handler handler;

    public ProjectSearchFragment(Handler handler) {
        this.handler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projectsearch, container, false);
    }

    private void init(){
        TreeRecyclerAdapter treeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);
        SimpleTreeItem simpleTreeItem = new SimpleTreeItem()//传入布局id.
                .onItemBind(new SimpleTreeItem.Consumer<ViewHolder>() {
                    @Override
                    public void accept(ViewHolder viewHolder) {

                    }
                })
                .onItemClick(new SimpleTreeItem.Consumer<ViewHolder>() {
                    @Override
                    public void accept(ViewHolder viewHolder) {

                    }
                });
    }
}
