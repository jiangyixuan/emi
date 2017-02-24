package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jyx.emi.Adapter.MyViewPagerAdapter;
import com.jyx.emi.R;
import com.jyx.emi.utils.PreferencesUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/19.
 */
public class GuideActivity extends Activity {

    private Button btnStart;
    private View viewRedPoint;
    //两个圆点间距离
    int mPointWidth;
    //引导原点的父控件
    private LinearLayout llPointGroup;
    ArrayList<ImageView> mImageViewList;
    private static final int[] mImageIds = new int[]{
            R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3
    };
    private ViewPager vpGuide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        init();
        initViews();

        vpGuide.setAdapter(new MyViewPagerAdapter(mImageViewList));
        vpGuide.setOnPageChangeListener(new GuidePageListener());

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //更新sp,表示已经展示新手引导
                PreferencesUtils.setBoolean("is_user_guide_showed", true, GuideActivity.this);

                boolean is_from_aboutActivity = PreferencesUtils.getBoolean("is_from_aboutActivity", false, GuideActivity.this);
                if (is_from_aboutActivity) {
                    finish();
                } else {
                    //跳转主页面
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void init() {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
        viewRedPoint = findViewById(R.id.view_red_point);
        btnStart = (Button) findViewById(R.id.but_start);
    }

    //初始化三个界面
    private void initViews() {
        mImageViewList = new ArrayList<ImageView>();

        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            //设置引导页背景
            image.setImageResource(mImageIds[i]);
            mImageViewList.add(image);
        }
        //初始化引导页的小圆点
        for (int i = 0; i < mImageIds.length; i++) {
            View point = new View(this);
            //设置引导页默认原点
            point.setBackgroundResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18, 18);
            //设置原点的大小
            point.setLayoutParams(params);

            if (i >= 1) {
                //设置原点间隔
                params.leftMargin = 25;
            }
            //将原点添加给线性布局
            llPointGroup.addView(point);
        }

        //获取视图树，对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            //当Layout执行结束此方法
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                llPointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//api16以上才能用
//				llPointGroup.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //measure(测量大小) layout(界面位置) ondraw
                mPointWidth = llPointGroup.getChildAt(1).getLeft() - llPointGroup.getChildAt(0).getLeft();
            }
        });
    }

    //viewpage滑动监听
    class GuidePageListener implements ViewPager.OnPageChangeListener {

        //滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        //滑动事件
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            int len = (int) (mPointWidth * arg1 + arg0 * mPointWidth);
            //获取当前红点的布局参数
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint.getLayoutParams();
            //设置左边距
            params.leftMargin = len;
            //重新给小红点设置布局参数
            viewRedPoint.setLayoutParams(params);
        }

        //某个页面被选中
        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            //最后一个界面
            if (arg0 == mImageIds.length - 1) {
                //显示开始体验的按钮
                btnStart.setVisibility(View.VISIBLE);
            } else {
                btnStart.setVisibility(View.INVISIBLE);
            }

        }


    }


}
