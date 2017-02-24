package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyx.emi.Adapter.RecommendAdapter;
import com.jyx.emi.R;

/**
 * Created by Administrator on 2016/5/14.
 */
public class AllCommendStoreActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_title;
    private GridView gv_all_store;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_store);
        init();
        initData();
        initView();
        listener();
    }

    private void init() {
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("品牌列表页");
        iv_back= (ImageView) findViewById(R.id.iv_back);
        gv_all_store= (GridView) findViewById(R.id.gv_all_store);
    }

    private void initData()
    {

    }

    private void initView()
    {
        gv_all_store.setAdapter(new RecommendAdapter(getApplicationContext(),true));
    }

    private void listener() {
        iv_back.setOnClickListener(this);
        gv_all_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AllCommendStoreActivity.this, StoreActivity.class);
                intent.putExtra("storeId", "1");
                intent.putExtra("brands", "森马");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title:
                finish();
                break;
            default:
                break;
        }

    }
}
