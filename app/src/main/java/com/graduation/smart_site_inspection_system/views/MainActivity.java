package com.graduation.smart_site_inspection_system.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.graduation.smart_site_inspection_system.Fragment_ProjectCheck.ProjectCheckFragment;
import com.graduation.smart_site_inspection_system.Fragment_ProjectSearch.ProjectSearchFragment;
import com.graduation.smart_site_inspection_system.Fragment_My.MyFragment;
import com.graduation.smart_site_inspection_system.R;
import com.graduation.smart_site_inspection_system.util.UserUtil;
import com.graduation.smart_site_inspection_system.views.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFl1;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;

    private ImageView[] imageViews;
    private int[] blackImage;  //未选中时的暗图标
    private int[] lightImage;  //选中时的亮图标
    private ProjectSearchFragment f1;
    private ProjectCheckFragment f2;
    private MyFragment f3;
    private FragmentManager fm;
    private FragmentTransaction ftr;//Fragment事务

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle;
            String data;
            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉顶部标题栏
        setContentView(R.layout.activity_main);

        init();
//        初始化底部图标
        imageViews = new ImageView[]{mImg1, mImg2, mImg3};
        blackImage = new int[]{R.drawable.ssis_home0_search
                , R.drawable.ssis_home0_check
                , R.drawable.ssis_home0_my};
        lightImage = new int[]{R.drawable.ssis_home1_search
                , R.drawable.ssis_home1_check
                , R.drawable.ssis_home1_my};

        fm = getSupportFragmentManager();

        for (int i = 0; i < imageViews.length; i++) {
            final int j = i;
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTab(j);
                }
            });
        }
//        加载默认的fragment
        showTab(0);
        //if (!UserUtil.isLoggedIn(this))
        //    startActivity(new Intent(this, LoginActivity.class));
    }

    private void showTab(int i) {
        ftr = fm.beginTransaction();//开启一个事务

        hideAllFragment(ftr);
        showColor(i);
        switch (i) {
            case 0:
                if (f1 == null) {
                    f1 = new ProjectSearchFragment(handler);
                    //填充到MainActivity
                    ftr.add(R.id.fl1, f1).commit();
                } else {
                    ftr.show(f1).commit();
                }
                break;
            case 1:
//                每次进入重新加载项目检查页面
                f2 = new ProjectCheckFragment(handler);
                ftr.add(R.id.fl1, f2).commit();
                break;
            case 2:
                if (f3 == null) {
                    f3 = new MyFragment(handler);
                    ftr.add(R.id.fl1, f3).commit();
                } else {
                    ftr.show(f3).commit();
                }
                break;
        }

    }

    private void showColor(int i) {
//        设置图标亮
        imageViews[i].setImageResource(lightImage[i]);
    }

    private void hideAllFragment(FragmentTransaction frt) {
//      隐藏所有的fragment
        if (f1 != null) ftr.hide(f1);
        if (f2 != null) ftr.remove(f2);  //刷新项目检查页面
        if (f3 != null) ftr.hide(f3);
//        设置图标暗
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setImageResource(blackImage[i]);
        }
    }

    private void init() {
        mFl1 = findViewById(R.id.fl1);
        mImg1 = findViewById(R.id.img1);
        mImg2 = findViewById(R.id.img2);
        mImg3 = findViewById(R.id.img3);
    }
}