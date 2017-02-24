package com.jyx.emi.fragment;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jyx.emi.R;

public class ShihuoFragment extends Fragment {



    private WebView wvShihuo;

    private View view;
    private Context context;

    public ShihuoFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_shihuo,null);

        init();

        loadWebView();

        return view;
    }

    private void loadWebView() {

                initWebView();
    }

    private void initWebView() {



        //开启脚本支持
        wvShihuo.getSettings().setJavaScriptEnabled(true);
        wvShihuo.getSettings().setBlockNetworkImage(false);
        //加载需要显示的网页
        wvShihuo.loadUrl("http://www.shihuo.cn/");
        wvShihuo.setWebViewClient(new webViewClient());


    }

    private void init() {
        wvShihuo = (WebView) view.findViewById(R.id.wv_shihuo);
    }
}

class webViewClient extends WebViewClient {
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}