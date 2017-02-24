package com.jyx.emi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jyx.emi.R;
import com.jyx.emi.activity.SearchActivity;

/**
 * Created by Administrator on 2016/4/24.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);

        init(view);
        listener(view);

        return view;
    }

    private void init(View view) {


    }

    private void listener(View view)
    {
        view.findViewById(R.id.ll_search).setOnClickListener(this);
    }


    private void initData()
    {

    }

    private void initView()
    {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }
}
