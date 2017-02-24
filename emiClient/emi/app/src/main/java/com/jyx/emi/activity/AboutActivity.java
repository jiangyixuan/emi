package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.wxapi.SendWXHelper;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/6/15.
 */
public class AboutActivity extends Activity implements View.OnClickListener {

    private ProgressBar pb_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }



    private void init() {

        pb_check = (ProgressBar) findViewById(R.id.pb_check);

        findViewById(R.id.tv_introduction).setOnClickListener(this);
        findViewById(R.id.tv_check_update).setOnClickListener(this);
        findViewById(R.id.tv_send_wxsession).setOnClickListener(this);
        findViewById(R.id.tv_store_add).setOnClickListener(this);
        findViewById(R.id.tv_blog).setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_title)).setText(R.string.about_emi);
        findViewById(R.id.iv_back).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_introduction:
                startActivity(new Intent(AboutActivity.this, IntroductionActivity.class));
                break;
            case R.id.tv_check_update:
                pb_check.setVisibility(View.VISIBLE);
                checkUpdate();
                break;
            case R.id.tv_send_wxsession:
//                new SendWXHelper(AboutActivity.this).sendUrl("http://blog.csdn.net/jyx2020/",
//                    getResources(), R.mipmap.icon, "小伙伴们，一米上线啦，快来下载啊", "首页", 0);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"一米上线啦，大家快来下载啊，下载地址是：https://github.com/jyx2020/emi");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
            case R.id.tv_store_add:
                startActivity(new Intent(AboutActivity.this, StoreAddActivity.class));
                break;
            case R.id.tv_blog:
                startActivity(new Intent(AboutActivity.this, ShowBlogActivity.class));
                break;
            default:
                break;

        }
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    //执行检查更新操作
                    Thread.sleep(1500);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_check.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "已是最新版本", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }).start();
    }


    /**
     * 得到应用程序的版本名称
     */

    private String getVersionName() {
        // 用来管理手机的APK
        PackageManager pm = getPackageManager();

        try {
            // 得到知道APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }
}
