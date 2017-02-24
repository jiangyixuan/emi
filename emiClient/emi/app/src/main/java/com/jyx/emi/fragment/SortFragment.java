package com.jyx.emi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.activity.MainActivity;

/**
 * Created by Administrator on 2016/4/24.
 */
public class SortFragment extends Fragment implements View.OnClickListener{

    private Fragment mContent;
    private Fragment fragment_sort_title_sort;
    private Fragment fragment_sort_title_brands;
    private MainActivity activity;
    private ImageView iv_menu;
    private TextView  tv_title_sort;
    private TextView  tv_title_brands;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=(MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.fragment_sort, null);
        init(view);
        initData();
        initView(view, savedInstanceState);

        listener();
        return view;
    }

    private void init(View view)
    {
        iv_menu= (ImageView) view.findViewById(R.id.iv_menu);
        tv_title_sort= (TextView) view.findViewById(R.id.tv_title_sort);
        tv_title_brands= (TextView) view.findViewById(R.id.tv_title_brands);
        fragment_sort_title_sort=new SortTitleSortFragment();
        fragment_sort_title_brands=new SortTitleBrandsFragment();
    }
    private void initData()
    {

    }

    private void initView(View view ,Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            mContent=getChildFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        if(mContent==null)
        {
            mContent=fragment_sort_title_sort;
        }
        //显示默认fragment
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_sort, fragment_sort_title_sort).commit();
    }
    private void listener()
    {
        iv_menu.setOnClickListener(this);
        tv_title_sort.setOnClickListener(this);
        tv_title_brands.setOnClickListener(this);
    }

    /*
     * 切换fragment,而不让fragment重新实例化
     */
    private void switchContent(Fragment from,Fragment to)
    {
        if (mContent != to) {
            mContent = to;

            if (!to.isAdded()) {// 先判断是否被add过
                getChildFragmentManager().beginTransaction()
                        .hide(from).add(R.id.fl_sort, to).commit();// 隐藏当前的fragment，add下一个到Activity中
            } else {
                getChildFragmentManager().beginTransaction()
                        .hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_menu://侧滑菜单
                activity.openMenu();
                break;
            case R.id.tv_title_sort:
                switchContent(mContent,fragment_sort_title_sort);
                tv_title_sort.setTextColor(this.getResources().getColor(R.color.sort_ll_bag_press));
                tv_title_sort.setBackgroundResource(R.drawable.switch_sort_button_selector);
                tv_title_brands.setTextColor(Color.BLACK);
                tv_title_brands.setBackgroundResource(R.drawable.switch_sort_button_normal);

                break;
            case R.id.tv_title_brands:
                switchContent(mContent,fragment_sort_title_brands);
                tv_title_brands.setTextColor(this.getResources().getColor(R.color.sort_ll_bag_press));
                tv_title_brands.setBackgroundResource(R.drawable.switch_sort_button_selector);
                tv_title_sort.setTextColor(Color.BLACK);
                tv_title_sort.setBackgroundResource(R.drawable.switch_sort_button_normal);

                break;
        }
    }
}
