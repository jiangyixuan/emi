package com.jyx.emi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.R;
import com.jyx.emi.bean.RecommendStoreOverview;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.io.ACache;
import com.jyx.emi.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class BrandsActivity extends Activity implements View.OnClickListener{

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if(listBrands!=null) {
                        gv_brands.setAdapter(new BrandsAdapter());
                    }
                    else{
                        Toast.makeText(BrandsActivity.this,R.string.net_error,Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    break;
            }
        }
    };
    private TextView tv_cancel;
    private TextView tv_clean;
    private GridView gv_brands;
    private LinearLayout ll_submit;

    private List<RecommendStoreOverview> listBrands;

    private  String smallSortId;//当前小分类id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        init();
        initData();
        initView();
        listener();
    }

    private void init() {
        Intent intent=this.getIntent();
        smallSortId=intent.getStringExtra("smallSortId");
        tv_clean= (TextView) findViewById(R.id.tv_clean);
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        ll_submit=(LinearLayout)findViewById(R.id.ll_submit);
        gv_brands= (GridView) findViewById(R.id.gv_brands);
    }

    private void initData()
    {
        if(ACache.get(BrandsActivity.this).getAsString("listBrands")==null)
        {
            HttpUtils.doPostAsyn(GlobalContants.RECOMMENDSTORE_URL,"flag="+"smallSortId"+"&"+"smallSortId="+smallSortId, new HttpUtils.RequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    ACache.get(BrandsActivity.this).put("listBrands",result,5*ACache.TIME_DAY);
                    listBrands=new Gson().fromJson(result ,new TypeToken<List<RecommendStoreOverview>>(){}.getType());
                    handler.sendEmptyMessage(0);
                }
            });
        }
        else
        {

            listBrands=new Gson().fromJson(ACache.get(BrandsActivity.this)
                    .getAsString("listBrands"), new TypeToken<List<RecommendStoreOverview>>(){}.getType());

            if(listBrands!=null) {
                gv_brands.setAdapter(new BrandsAdapter());
            }
            else{
                Toast.makeText(BrandsActivity.this,R.string.net_error,Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void initView() {

    }
    private void listener() {
        tv_cancel.setOnClickListener(this);
        tv_clean.setOnClickListener(this);
        ll_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_clean:
                break;
            case R.id.ll_submit:
                ACache.get(this).put("comm_over", "");
                startActivity(new Intent(BrandsActivity.this, CommOverviewActivity.class));


                break;
            default:
                break;
        }
    }

    class BrandsAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return listBrands.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.gridview_item_brands,null);


            return view ;
        }
    }

}
