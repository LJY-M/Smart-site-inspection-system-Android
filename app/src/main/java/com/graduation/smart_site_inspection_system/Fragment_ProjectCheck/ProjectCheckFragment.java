package com.graduation.smart_site_inspection_system.Fragment_ProjectCheck;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.graduation.smart_site_inspection_system.R;


public class ProjectCheckFragment extends Fragment {
    private Handler handler;

    public ProjectCheckFragment(Handler handler) {
        this.handler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_projectcheck, container, false);
    }
}
