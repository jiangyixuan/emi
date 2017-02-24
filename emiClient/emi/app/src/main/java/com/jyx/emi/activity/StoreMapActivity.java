package com.jyx.emi.activity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.fragment.MapBusFragment;
import com.jyx.emi.fragment.MapDriverFragment;
import com.jyx.emi.fragment.MapRouteBaseFragment;
import com.jyx.emi.fragment.MapWalkFragment;
import com.jyx.emi.utils.PreferencesUtils;
import com.jyx.emi.utils.TransferDataUtils;

/**
 * Created by Administrator on 2016/6/10.
 */
public class StoreMapActivity extends Activity implements View.OnClickListener{

    private TextView tv_drive,tv_bus,tv_walk;
    private MapRouteBaseFragment fl_driver;
    private MapRouteBaseFragment fl_bus;
    private MapRouteBaseFragment fl_walk;
    private String storeId;
    private int defaultTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_store);
        init();
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_map, fl_driver).commit();

    }

    public void init() {

        tv_drive= (TextView) findViewById(R.id.tv_drive);
        tv_bus= (TextView) findViewById(R.id.tv_bus);
        tv_walk= (TextView) findViewById(R.id.tv_walk);
        fl_driver=new MapDriverFragment();
        fl_bus=new MapBusFragment();
        fl_walk=new MapWalkFragment();

        tv_drive.setOnClickListener(this);
        tv_bus.setOnClickListener(this);
        tv_walk.setOnClickListener(this);
        defaultTextColor=tv_bus.getTextColors().getDefaultColor();
        ((TextView)findViewById(R.id.tv_title)).setText("路线");
        findViewById(R.id.iv_back).setOnClickListener(this);

        storeId=getIntent().getStringExtra("storeId");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_drive://要想用户体验更好，最好判断是否点击当前fragment
                tv_drive.setTextColor(Color.WHITE);
                tv_bus.setTextColor(defaultTextColor);
                tv_walk.setTextColor(defaultTextColor);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_map, fl_driver).commit();
                break;
            case R.id.tv_bus:
                tv_drive.setTextColor(defaultTextColor);
                tv_bus.setTextColor(Color.WHITE);
                tv_walk.setTextColor(defaultTextColor);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_map, fl_bus).commit();
                break;
            case R.id.tv_walk:
                tv_drive.setTextColor(defaultTextColor);
                tv_bus.setTextColor(defaultTextColor);
                tv_walk.setTextColor(Color.WHITE);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fl_map, fl_walk).commit();
                break;
            default:break;
        }
    }

    public String getStoreId()
    {
        return storeId;
    }

}
