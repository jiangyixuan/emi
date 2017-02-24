package com.jyx.emi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.RecyclerCommoverAdapter;
import com.jyx.emi.R;
import com.jyx.emi.bean.CommOverview;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class StoreActivity extends Activity implements View.OnClickListener{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if(commOverviews!=null&&commOverviews.size()!=0) {

                        rv_commover.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        rv_commover.setAdapter(commoverAdapter);
                        listener();
                    }else
                    {
                        Toast.makeText(getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
                        findViewById(R.id.vs_net_error).setVisibility(View.VISIBLE);
                    }
                    findViewById(R.id.activity_store).setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                    break;
            }
        }
    };

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_go_map;
    private ProgressDialog mProgressDialog;
    private String storeId;
    private RecyclerView rv_commover;

    private RecyclerCommoverAdapter commoverAdapter;
    private ArrayList<CommOverview> commOverviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        init();
        initData();
        initView();

    }

    private void listener() {

        commoverAdapter.setOnItemClickListener(new RecyclerCommoverAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(StoreActivity.this, CommDetailActivity.class);

                intent.putExtra("commId", commOverviews.get(position).getCommId() + "");
                intent.putExtra("commName", commOverviews.get(position).getCommName());
                intent.putExtra("nowPrice", commOverviews.get(position).getNowPrice());
                intent.putExtra("oldPrice", commOverviews.get(position).getOldPrice());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int postion) {

            }
        });
    }

    private void initData() {
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        HttpUtils.doPostAsyn(GlobalContants.STORE_COMMOVER, "storeId=" + storeId, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                commOverviews=new Gson().fromJson(result, new TypeToken<List<CommOverview>>(){}.getType());
                commoverAdapter=new RecyclerCommoverAdapter(StoreActivity.this,commOverviews);

                handler.sendEmptyMessage(0);
            }

        });
    }

    private void initView() {
        
    }

    private void init()
    {
        Intent intent=getIntent();
        storeId=intent.getStringExtra("storeId");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_go_map= (TextView) findViewById(R.id.tv_go_map);
        tv_title.setText(intent.getStringExtra("brands"));
        rv_commover= (RecyclerView) findViewById(R.id.rv_commover);

        iv_back.setOnClickListener(this);
        tv_go_map.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_go_map://进入商家地图
                Intent intent = new Intent(this, StoreMapActivity.class);
                intent.putExtra("storeId",storeId);
                startActivity(intent);
                break;

        }

    }
}
