package com.jyx.emi.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.activity.StoreMapActivity;
import com.jyx.emi.bean.CommDetail;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CommDetailAdvisoryFragment extends Fragment implements View.OnClickListener{

    private TextView tv_store_tel;
    private TextView tv_store_position;
    private TextView tv_tel;
    private TextView tv_go_map;
    private CommDetail commDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advisory_commdetail, null);
        init(view);
        listener();
        return view;
    }

    private void init(View view) {
        tv_store_tel= (TextView) view.findViewById(R.id.tv_store_tel);
        tv_store_position= (TextView) view.findViewById(R.id.tv_store_position);
        tv_tel= (TextView) view.findViewById(R.id.tv_tel);
        tv_go_map= (TextView) view.findViewById(R.id.tv_go_map);

    }

    private void listener()
    {
        tv_tel.setOnClickListener(this);
        tv_go_map.setOnClickListener(this);
    }

    /**
     * 初始化视图，供activity获取完数据回调时调用
     * @param commDetail
     */
    public void initView(CommDetail commDetail)
    {
        tv_store_tel.setText(commDetail.getStoreTel());
        tv_store_position.setText(commDetail.getStorePosition());
        this.commDetail=commDetail;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.tv_tel://启动拨打电话

                startActivity(new Intent()
                        .setAction(Intent.ACTION_CALL)
                        .setData(Uri.parse("tel:" + tv_store_tel.getText())));
                break;
            case R.id.tv_go_map://进入商家地图
                Intent intent = new Intent(getActivity(), StoreMapActivity.class);

                intent.putExtra("storeId",commDetail.getStoreId()+"");
                startActivity(intent);
                break;
        }
    }
}
