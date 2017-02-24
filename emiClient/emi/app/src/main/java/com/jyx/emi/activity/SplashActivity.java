package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.jyx.emi.R;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.io.ACache;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.PreferencesUtils;

/**
 * Created by Administrator on 2016/4/24.
 */
public class SplashActivity extends Activity {

    private final static String TAG="SplashActivity";

    RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
        rlRoot=(RelativeLayout) findViewById(R.id.rl_root);
        startAnim();

    }

    //初始化应用数据
    private void initData()
    {
        //初始化分类页面首页数据
        initSortData();
    }

    //开启动画
    private void startAnim()
    {
        //动画集合
        AnimationSet set=new AnimationSet(false);

        //旋转动画
        RotateAnimation rotate=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setDuration(1000);//动画时间
        rotate.setFillAfter(true);//保持动画状态

        //缩放动画
        ScaleAnimation scale=new ScaleAnimation(0, 1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        //渐变动画
        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(2000);
        alpha.setFillAfter(true);

        set.addAnimation(rotate);
        set.addAnimation(scale);
        set.addAnimation(alpha);

        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            //动画执行结束
            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                jumpNextPage();
                finish();
            }
        });

        rlRoot.startAnimation(set);
    }

    //跳转下一个页面
    private void jumpNextPage()
    {
        boolean userGuide= PreferencesUtils.getBoolean("is_user_guide_showed", false, this);

        Log.i("asdasd",userGuide+"哈哈");

        if(!userGuide)
        {
            //跳转到新手引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        }
        else
        {
			startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }
        finish();
    }

    public void initSortData()
    {

        ACache cache=ACache.get(this);
        if (cache.getAsString("home_sort")==null)
        {
            //加入缓存数据为空，则发起请求，更新缓存
            HttpUtils.doGetAsyn(GlobalContants.RECOMMEND1_URL, new HttpUtils.RequestCallBack() {
                @Override
                public void onSuccess(String result) {

                    ACache cache = ACache.get(SplashActivity.this);
                    cache.put("home_sort", result, 3 * ACache.TIME_DAY);
                }

            });
        }
        else
        {

        }

    }

}
