package com.jyx.emi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jyx.emi.R;
import com.jyx.emi.activity.AboutActivity;
import com.jyx.emi.activity.CollectActivity;
import com.jyx.emi.activity.CommActivity;
import com.jyx.emi.activity.LoginActivity;
import com.jyx.emi.activity.PersonalActivity;
import com.jyx.emi.bean.User;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.utils.PreferencesUtils;
import com.jyx.emi.utils.TransferDataUtils;


/**
 * Created by Administrator on 2016/4/24.
 */
public class LeftFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_login;
    private LinearLayout ll_collect;
    private LinearLayout ll_bbs;
    private LinearLayout ll_clean_cache;
    private LinearLayout ll_about;

    private ImageLoader imageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_menu, null);
        init(view);
//        initView();
        listener();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    /**
     * 初始化视图数据
     */
    public void initView() {

        if (PreferencesUtils.getBoolean("isLogin", false, getActivity())) {

            ((TextView) ll_login.getChildAt(1)).setText(TransferDataUtils.getString("name", "个人中心", getActivity()));
            String headPortraitUrl = TransferDataUtils.getString("headPortraitUrl", null, getActivity());
            if (!TextUtils.isEmpty(headPortraitUrl)) {
                imageLoader.bindBitmap(headPortraitUrl, ((ImageView) ll_login.getChildAt(0)), 100, 100);
            }
        } else {
            //未登录显示默认
            ((TextView) ll_login.getChildAt(1)).setText("注册/登录");
            ((ImageView) ll_login.getChildAt(0)).setImageResource(R.mipmap.head_portrait);
        }

    }


    private void init(View view) {
        ll_login = (LinearLayout) view.findViewById(R.id.ll_login);
        ll_collect = (LinearLayout) view.findViewById(R.id.ll_collect);
        ll_bbs = (LinearLayout) view.findViewById(R.id.ll_bbs);
        ll_clean_cache = (LinearLayout) view.findViewById(R.id.ll_clean_cache);
        ll_about = (LinearLayout) view.findViewById(R.id.ll_about);
        imageLoader = ImageLoader.build(getActivity());
    }

    private void listener() {
        ll_login.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_bbs.setOnClickListener(this);
        ll_clean_cache.setOnClickListener(this);
        ll_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_login:

                //判断是否登录
                if (!PreferencesUtils.getBoolean("isLogin", false, getActivity())) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PersonalActivity.class));
                }
                break;
            case R.id.ll_collect:
                if (PreferencesUtils.getBoolean("isLogin", false, getActivity())) {
                    startActivity(new Intent(getActivity(), CollectActivity.class));
                } else {
                    Toast.makeText(getActivity(), R.string.notlogin, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
            case R.id.ll_bbs:

//                startActivity(new Intent(getActivity(), CommActivity.class));

                Toast.makeText(getActivity(), R.string.bbs_niece, Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_clean_cache:
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "清理成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            default:
                break;
        }
    }
}
