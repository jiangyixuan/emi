package com.jyx.emi.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.DateUtils;
import com.jyx.emi.wxapi.SendWXHelper;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Date;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CommDetailTopbarFragment extends Fragment implements View.OnClickListener {

    private ImageView iv_back, iv_share;
    private TextView tv_time;
    private PopupWindow popupWindow;

    private IWXAPI api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topbar_commdetail, null);

        init(view);
        listener();

        return view;
    }

    private void init(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        api = WXAPIFactory.createWXAPI(getActivity(), GlobalContants.APP_ID);
        api.registerApp(GlobalContants.APP_ID);
    }

    private void listener() {
        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_share://点击了分享商品按钮
                showWindow(v);
                break;
        }
    }

    /**
     * 显示倒计时,根据传入时间字符串
     */
    public void showCountdown(String date) {

        new MyCountDownTimer(DateUtils.dateToLong(date)-System.currentTimeMillis(), 1000).start();

    }

    /**
     * 显示泡泡窗口
     */
    private void showWindow(View parent) {
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_share, null);
        TextView tv_share_wxsession = (TextView) view.findViewById(R.id.tv_share_wxsession);
        TextView tv_share_wxtimeline = (TextView) view.findViewById(R.id.tv_share_wxtimeline);
        popupWindow = new PopupWindow(view, 300, 160);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        popupWindow.showAsDropDown(parent, xPos, 0);

        tv_share_wxsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendWXHelper(getActivity()).sendUrl("http://blog.csdn.net/jyx2020/",
                        getResources(), R.mipmap.icon, "一米内测中，获取最新消息请关注作者博客", "首页", 0);
            }
        });
        tv_share_wxtimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendWXHelper(getActivity()).sendUrl("http://blog.csdn.net/jyx2020/",
                        getResources(), R.mipmap.icon,"一米内测中，获取最新消息请关注作者博客","首页",1);
            }
        });
    }

    /**
     * 倒计时类
     */
    class MyCountDownTimer extends CountDownTimer
    {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            tv_time.setText(
                    millisUntilFinished/(24*60*60*1000)+"天"
                            +millisUntilFinished/1000/60/60%24+"小时"
                            +millisUntilFinished/1000/60%60+"分"
                            +(millisUntilFinished/1000)%60+"秒");

        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            tv_time.setText("0天");
        }

    }


}
