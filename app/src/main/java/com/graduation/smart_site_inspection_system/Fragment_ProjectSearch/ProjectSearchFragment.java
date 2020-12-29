package com.graduation.smart_site_inspection_system.Fragment_ProjectSearch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSON;
import com.baozi.treerecyclerview.adpater.TreeRecyclerAdapter;
import com.baozi.treerecyclerview.adpater.TreeRecyclerType;
import com.baozi.treerecyclerview.base.BaseRecyclerAdapter;
import com.baozi.treerecyclerview.base.ViewHolder;
import com.baozi.treerecyclerview.factory.ItemHelperFactory;
import com.baozi.treerecyclerview.item.SimpleTreeItem;
import com.baozi.treerecyclerview.item.TreeItem;
import com.graduation.smart_site_inspection_system.Bean.ProjectTree.ClientBean;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.HttpUtil;
import com.graduation.smart_site_inspection_system.util.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;


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

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        final SwipeRefreshLayout swip_refresh_layout = getView().findViewById(R.id.swipeLayout);
        swip_refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getShelfProject();
            }
        });

        final TreeRecyclerAdapter treeRecyclerAdapter = new TreeRecyclerAdapter(TreeRecyclerType.SHOW_EXPAND);
        RecyclerView recyclerView = getView().findViewById(R.id.rv_content);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(treeRecyclerAdapter);
//        treeRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull ViewHolder viewHolder, int position) {
//                Toast.makeText(getContext(), "点击了"+position, Toast.LENGTH_SHORT).show();
//                treeRecyclerAdapter.getItemManager().getItem(position).onClick(viewHolder);
//            }
//        });
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                swip_refresh_layout.setRefreshing(false);
                List<TreeItem> items = (List<TreeItem>) msg.obj;
                if (items.size() > 0) {
                    treeRecyclerAdapter.getItemManager().replaceAllItem(items);
                    getView().findViewById(R.id.tv_noData).setVisibility(View.GONE);
                }else getView().findViewById(R.id.tv_noData).setVisibility(View.VISIBLE);
            }
        };
        if (UserUtil.isLoggedIn())
            getShelfProject();

        //treeRecyclerAdapter.getItemManager().replaceAllItem(items);
        //        new Thread() {
//            @Override
//            public void run() {
        //super.run();
                /*String string = getFromAssets("testproject.txt");
                List<ClientBean> clientBeans = JSON.parseArray(string, ClientBean.class);
                List<TreeItem> items = ItemHelperFactory.createItems(clientBeans);
                treeRecyclerAdapter.getItemManager().replaceAllItem(items);*/
//            }
//        }.start();
    }

    private void getShelfProject() {
        new Thread() {
            @Override
            public void run() {
                HashMap<String, String> options = new HashMap<>();
                List<ClientBean> clientBeans = HttpUtil.shelfProjects_Get(options);
                List<TreeItem> items = ItemHelperFactory.createItems(clientBeans);
                Message message = new Message();
                message.obj = items;
                handler.sendMessage(message);
            }
        }.start();
    }

    public String getFromAssets(String fileName) {
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
