package com.jyx.emi.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyx.emi.Adapter.FragmentAdapter;
import com.jyx.emi.R;
import com.jyx.emi.fragment.PostsFragment;
import com.jyx.emi.fragment.ShihuoFragment;

import java.util.ArrayList;
import java.util.List;

public class CommActivity extends FragmentActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private TextView tvPosts;
    private TextView tvShihuo;
    private ViewPager vpSlide;
    public List<Fragment> lsFrag;
    private View viewCursor;
    // 记录当前选中的tab的index
    public int currentIndex = 0;
    // 指示器的偏移量
    public int offset = 0;
    // 左margin
    public int leftMargin = 0;
    // 屏幕宽度
    public int screenWidth = 0;
    public int screen1_2;
    //
    public LinearLayout.LayoutParams lp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_comm);

        init();

        initViewPager();

    }

    private void init() {

        ((TextView) findViewById(R.id.tv_title)).setText(R.string.comm_string);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screen1_2 = screenWidth / 2;

        viewCursor = findViewById(R.id.view_cursor);
        lp = (LinearLayout.LayoutParams) viewCursor.getLayoutParams();
        leftMargin = lp.leftMargin;

        tvPosts = (TextView) findViewById(R.id.tv_posts);
        tvShihuo = (TextView) findViewById(R.id.tv_shihuo);
        vpSlide = (ViewPager) findViewById(R.id.vp_slide);

        tvPosts.setText("社区");
        tvShihuo.setText("识货");

        tvPosts.setOnClickListener(this);
        tvShihuo.setOnClickListener(this);
    }

    private void initViewPager(){
        vpSlide = (ViewPager) findViewById(R.id.vp_slide );
        lsFrag = new ArrayList<Fragment>();
        Fragment fragment = new PostsFragment(CommActivity.this);
        lsFrag.add(fragment);
        fragment = new ShihuoFragment(CommActivity.this);
        lsFrag.add(fragment);

        vpSlide.setAdapter(new FragmentAdapter(getSupportFragmentManager(), lsFrag));
        vpSlide.setCurrentItem(0);
        vpSlide.setOnPageChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_posts:
                vpSlide.setCurrentItem(0);
                break;
            case R.id.tv_shihuo:
                vpSlide.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        offset = (int) (screen1_2 - viewCursor.getLayoutParams().width * 1.5);
        if(position == 0){
            lp.leftMargin = (positionOffsetPixels / 2) + offset + 60;
        }else if(position == 1){
            lp.leftMargin = (positionOffsetPixels / 2) + screen1_2 + 60;
        }
        viewCursor.setLayoutParams(lp);
        currentIndex = position;

        switch (position){
            case 0 :
                tvPosts.setTextColor(getResources().getColor(R.color.main));
                tvShihuo.setTextColor(getResources().getColor(R.color.comm_color));
                break;
            case 1:
                tvShihuo.setTextColor(getResources().getColor(R.color.main));
                tvPosts.setTextColor(getResources().getColor(R.color.comm_color));
        }
    }

    @Override
    public void onPageSelected(int position) {
        //载入
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
