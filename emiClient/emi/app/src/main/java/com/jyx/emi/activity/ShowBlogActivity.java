package com.jyx.emi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jyx.emi.R;

/**
 * Created by Administrator on 2016/6/15.
 */
public class ShowBlogActivity extends Activity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        init();
    }

    private void init()
    {
        findViewById(R.id.bt_forward).setVisibility(View.GONE);
        webView = (WebView) findViewById(R.id.webView);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                mProgressDialog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog.show(ShowBlogActivity.this, null, "正在加载...");
                }

            }
        });
        webView.loadUrl("https://jiangyixuan.github.io");

        findViewById(R.id.iv_back).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_title)).setText(R.string.writer_blog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
