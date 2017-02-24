package com.jyx.emi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jyx.emi.Adapter.CommoverAdapter;
import com.jyx.emi.R;
import com.jyx.emi.bean.CommCollect;
import com.jyx.emi.bean.CommOverview;
import com.jyx.emi.db.CollectHelper;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * initialize engine failed
 * Created by Administrator on 2016/6/4.
 */
public class CollectActivity extends Activity implements View.OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    topRevert();
                    if (commOverviews != null) {
                        commoverAdapter = new CommoverAdapter(CollectActivity.this, commOverviews);
                        gv_pull_refresh.setAdapter(commoverAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                        ((ViewStub) findViewById(R.id.vs_net_error)).setVisibility(View.VISIBLE);

                    }
                    //隐藏下拉刷新头部和正在加载提示
                    mProgressDialog.dismiss();
                    gv_pull_refresh.onRefreshComplete();

                    break;

                default:
                    break;
            }
        }
    };
    private ImageView iv_back;
    private TextView tv_edit;
    private PullToRefreshGridView gv_pull_refresh;
    private ProgressDialog mProgressDialog;

    private List<CommOverview> commOverviews;
    private CommoverAdapter commoverAdapter;
    private List<CommCollect> commCollects;
    private boolean isEdit=false;
    private ImageView  iv_delete_collect;//删除收藏按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        init();
        initData();
        listener();

    }

    private void initData() {
        topRevert();
        commCollects = new CollectHelper(this).getCommCollects();
        if (commCollects.size() == 0) {
            ((ViewStub) findViewById(R.id.vs_not_collect)).setVisibility(View.VISIBLE);
        } else {
            mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
            requestDataFromServer();
        }


    }


    private void init() {

        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        gv_pull_refresh = (PullToRefreshGridView) findViewById(R.id.gv_pull_refresh);

        iv_back.setOnClickListener(this);
        tv_edit.setOnClickListener(this);

        gv_pull_refresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ILoadingLayout startLabels = gv_pull_refresh
                .getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("放开以刷新...");
        startLabels.setReleaseLabel("正在刷新...");

    }


    private void listener() {
        gv_pull_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

                requestDataFromServer();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {


            }
        });
        gv_pull_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CollectActivity.this, CommDetailActivity.class);

                intent.putExtra("commId", commOverviews.get(position).getCommId() + "");
                intent.putExtra("commName", commOverviews.get(position).getCommName());
                intent.putExtra("nowPrice", commOverviews.get(position).getNowPrice());
                intent.putExtra("oldPrice", commOverviews.get(position).getOldPrice());
                startActivity(intent);

            }
        });
    }
    private void requestDataFromServer() {

        HttpUtils.doPostAsyn(GlobalContants.COLLECT_URL,
                "ids=" + JsonUtil.createJsonString(commCollects), new HttpUtils.RequestCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        commOverviews = new Gson().fromJson(result, new TypeToken<ArrayList<CommOverview>>() {
                        }.getType());
                        handler.sendEmptyMessage(0);

                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit://编辑按钮

                iv_delete_collect= (ImageView) gv_pull_refresh.getRootView().findViewById(R.id.iv_delete_collect);
                if(isEdit){
                    isEdit=false;
                    topRevert();
                }
                else{
                    if(commCollects==null||commCollects.size()==0) {
                        Toast.makeText(CollectActivity.this,R.string.not_collect,Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isEdit=true;
                    topChange();
                }
                break;
        }
    }

    /**
     * 导航栏置为编辑状态
     */
    private void topChange()
    {
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.tv_title)).setText("编辑商品");
        tv_edit.setText("完成");
        ImageView iv_comm_img= (ImageView) gv_pull_refresh.getRootView().findViewById(R.id.iv_comm_img);
        if(iv_comm_img!=null) {
            iv_comm_img.setAlpha(0.5f);
            iv_delete_collect.setVisibility(View.VISIBLE);//显示删除收藏按钮
            iv_delete_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog dialog = createDialog();
                    dialog.show();

                }
            });
        }


    }  /**
     * 导航栏置为初始状态
     */
    private void topRevert()
    {
        findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.tv_title)).setText("我的收藏");
        tv_edit.setText("编辑");

        ImageView iv_comm_img= (ImageView) gv_pull_refresh.getRootView().findViewById(R.id.iv_comm_img);
        if(iv_comm_img!=null) {
            iv_comm_img.setAlpha(1);
        }
        if(iv_delete_collect!=null) {
            iv_delete_collect.setVisibility(View.GONE);//隐藏删除收藏按钮
        }
    }

    /**
     * 确认取消对话框
     */
    //定义对话框
    private AlertDialog createDialog()
    {
        AlertDialog.Builder ad=new AlertDialog.Builder(CollectActivity.this);
//        ad.setTitle("你要取消收藏此商品吗");
        ad.setMessage("你确定要取消收藏此商品吗");
        ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                topRevert();
            }
    });
    ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            new CollectHelper(CollectActivity.this).deleteCollect("1");//???
            initData();
        }
    });

        return ad.create();
    }

}
