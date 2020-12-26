package com.graduation.smart_site_inspection_system.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.graduation.smart_site_inspection_system.Bean.UserBean;
import com.graduation.smart_site_inspection_system.R;

public class MyDetailActivity extends AppCompatActivity {

    private TextView mAccount;
    private TextView mName;
    private TextView mTelephone;
    private TextView mSex;
    private TextView mAddress;
    private ImageView back;  //返回

    private Bundle mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);  //去掉顶部标题栏
        setContentView(R.layout.activity_my_detail);

        init();  //控件初始化
//        返回监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void init() {
        back = (ImageView)findViewById(R.id.my_detail_back);
        mAccount = (TextView)findViewById(R.id.my_detail_account);
        mName = (TextView)findViewById(R.id.my_detail_name);
        mTelephone = (TextView)findViewById(R.id.my_detail_telephone);
        mSex = (TextView)findViewById(R.id.my_detail_sex);
        mAddress = (TextView)findViewById(R.id.my_detail_address);

        mUser = getIntent().getBundleExtra("mUser");
        mAccount.setText(mUser.getString("account"));
        mName.setText(mUser.getString("name"));
        mTelephone.setText(mUser.getString("telephone"));
        mSex.setText(mUser.getString("sex"));
        mAddress.setText(mUser.getString("address"));
    }
}