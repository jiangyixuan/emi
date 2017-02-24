package com.jyx.emi.Adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jyx.emi.activity.CommBigPictureActivity;
import com.jyx.emi.activity.CommOverviewActivity;

import java.util.ArrayList;

/**
 * viewPager适配器
 * Created by Administrator on 2016/5/30.
 */
public class ImagePagerAdapter extends PagerAdapter{

    private ArrayList<ImageView> mViewList;
    private int pagerNum = 0;

    public ImagePagerAdapter(ArrayList<ImageView> viewList,int pagerNum) {
        mViewList = viewList;
        this.pagerNum=pagerNum;
    }

    public int getPagerNum() {
        return pagerNum;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        if (mViewList.get(arg1) != null) {
            ((ViewPager) arg0).removeView(mViewList.get(arg1));
        }
    }

    @Override
    public Object instantiateItem(View arg0, final int arg1) {
        try {
            if (mViewList.get(arg1).getParent() == null) {
                ((ViewPager) arg0).addView(mViewList.get(arg1), 0);
            } else {

                ((ViewGroup) mViewList.get(arg1).getParent())
                        .removeView(mViewList.get(arg1));
                ((ViewPager) arg0).addView(mViewList.get(arg1), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pagerNum = arg1;
        }

        mViewList.get(arg1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂时还未跳转

            }
        });

        return mViewList.get(arg1);
    }


}
