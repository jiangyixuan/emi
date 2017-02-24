package com.jyx.emi.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jyx.emi.R;
import com.jyx.emi.fragment.GoFragment;
import com.jyx.emi.fragment.HomeFragment;
import com.jyx.emi.fragment.LeftFragment;
import com.jyx.emi.fragment.SortFragment;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener{
    private Fragment mContent;
    private Fragment fl_home, fl_sort, fl_go;
    private LeftFragment fl_left;
    public SlidingMenu menu;
    private LinearLayout ll_home, ll_sort, ll_go;
    private long exitTime = 0;
    private ImageView iv_home, iv_sort, iv_go;
    private TextView tv_home, tv_sort, tv_go;

    //    private CallAddress callAddress;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        fl_left.initView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initSlidingMenu(savedInstanceState);
        initSlidingMenu(savedInstanceState);
        //设置默认显示HomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_home, mContent).commit();

        //    LocationUtil locationUtils=new LocationUtil();
        //    locationUtils.initLocation(getApplicationContext(),new MyLocationListener(),null);

        ll_go.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_sort.setOnClickListener(this);
    }
    private void init()
    {
        //初始化
        ll_home=(LinearLayout) findViewById(R.id.ll_home);
        ll_sort=(LinearLayout) findViewById(R.id.ll_sort);
        ll_go=(LinearLayout) findViewById(R.id.ll_go);
        fl_home=new HomeFragment();
        fl_sort=new SortFragment();
        fl_go=new GoFragment();
        fl_left=new LeftFragment();

        iv_home=(ImageView) findViewById(R.id.iv_home);
        iv_sort=(ImageView) findViewById(R.id.iv_sort);
        iv_go=(ImageView) findViewById(R.id.iv_go);
        tv_home=(TextView) findViewById(R.id.tv_home);
        tv_sort=(TextView) findViewById(R.id.tv_sort);
        tv_go=(TextView) findViewById(R.id.tv_go);
    }
    /*
	 * 初始化侧滑菜单
	 */
    private void initSlidingMenu(Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            mContent=(HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState,"mContent");
        }
        if(mContent==null)
        {
            mContent=fl_home;
        }
        setBehindContentView(R.layout.fragment_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_left, fl_left).commit();
        //实例化菜单对象
        menu=getSlidingMenu();
        //设置可以左右滑动的菜单
        menu.setMode(SlidingMenu.LEFT);
        //设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置滑动时菜单的是否淡入淡出
        menu.setFadeEnabled(true);
        //设置渐入渐出的效果
//		menu.setFadeDegree(0.8f);
        //设置触摸屏幕的模式，这里设置为全屏
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置滑动时拖拽效果
//		menu.setBehindScrollScale(0.4f);
        //缩放动画
        menu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {

            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                //原图从3/4增加到1倍大小
                float scale = (float) (0.75 + 0.25 * percentOpen);
                //x坐标先向屏幕左边移动1/4的视图宽度，然后再慢慢移动到0，这样来实现从屏幕左边移动进屏幕的效果
                canvas.translate(-canvas.getWidth()/4 + percentOpen * canvas.getWidth()/4, 0);
                //x，y方向同时放大，动画的相对中心定在“右中”
                canvas.scale(scale, scale, canvas.getWidth(), canvas.getHeight() / 2);

            }
        });
    }

    @Override
    public void onClick(View v) {
//	设置点击监听
        switch (v.getId())
        {
            case R.id.ll_home://点击首页
                switchContent(mContent,fl_home);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                switchColor();
                iv_home.setImageResource(R.mipmap.home_press);
                tv_home.setTextColor(Color.rgb(179, 69, 71));

                break;
            case R.id.ll_sort://点击分类
                switchContent(mContent,fl_sort);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                switchColor();
                iv_sort.setImageResource(R.mipmap.sort_press);
                tv_sort.setTextColor(Color.rgb(179, 69, 71));
                break;
            case R.id.ll_go://点击带你去
                switchContent(mContent,fl_go);
                menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                switchColor();
                iv_go.setImageResource(R.mipmap.go_press);
                tv_go.setTextColor(Color.rgb(179, 69, 71));
                break;
            default:
                break;
        }
    }
    /*
	 * 将底部颜色设为默认
	 */
    private void switchColor()
    {
        iv_home.setImageResource(R.mipmap.home);
        iv_sort.setImageResource(R.mipmap.sort);
        iv_go.setImageResource(R.mipmap.go);
        tv_home.setTextColor(Color.BLACK);
        tv_sort.setTextColor(Color.BLACK);
        tv_go.setTextColor(Color.BLACK);
    }
    /*
     * 切换fragment,而不让fragment重新实例化
     */
    private void switchContent(Fragment from,Fragment to)
    {
        if (mContent != to) {
            mContent = to;

            if (!to.isAdded()) {// 先判断是否被add过
                getSupportFragmentManager().beginTransaction()
                        .hide(from).add(R.id.fl_home, to).commit();// 隐藏当前的fragment，add下一个到Activity中
            } else {
                getSupportFragmentManager().beginTransaction()
                        .hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }

        }
    }

    public void openMenu()
    {
        //打开侧滑菜单
        menu.toggle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //返回键监听
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime)>2000) {
                Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();

            } else {
                System.exit(0);
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
