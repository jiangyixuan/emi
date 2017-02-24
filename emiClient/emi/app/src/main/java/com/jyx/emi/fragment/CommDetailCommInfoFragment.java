package com.jyx.emi.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.activity.CommDetailActivity;
import com.jyx.emi.activity.CommScaleImageActivity;
import com.jyx.emi.activity.StoreActivity;
import com.jyx.emi.bean.CommDetail;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.utils.MyUtils;

/**
 * Created by Administrator on 2016/5/26.
 */
public class CommDetailCommInfoFragment extends Fragment implements View.OnClickListener {

    private TextView tv_brands, tv_comm_name, tv_material, tv_washing,
            tv_productionplace, tv_remark, tv_number, tv_go_store;
    private ImageView iv_commdetail;
    private CommDetail commDetail;//该商品详细信息类
    private CommDetailActivity activity;//所在Activity
    private ImageLoader imageLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        activity = (CommDetailActivity) getActivity();
        commDetail=activity.getCommDetail();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_commdetail, null);
        init(view);
        initData();
        initView();
        listener();
        return view;

    }

    private void listener() {
        tv_go_store.setOnClickListener(this);
        iv_commdetail.setOnClickListener(this);
    }

    private void initData() {


    }

    private void initView() {
        if(commDetail!=null)
        {
            tv_brands.setText(commDetail.getBrands());
            tv_material.setText(commDetail.getMaterial());
            tv_washing.setText(commDetail.getWashing());
            tv_productionplace.setText(commDetail.getProductionplace());
            tv_remark.setText(commDetail.getRemark());
            tv_number.setText(commDetail.getNumber());
            int screenWidth = MyUtils.getScreenMetrics(getActivity()).widthPixels;
            imageLoader.bindBitmap(GlobalContants.SERVER_URL + commDetail.getImgDetail(), iv_commdetail, screenWidth, screenWidth);
            tv_go_store.setText("进入" + commDetail.getBrands());
        }
        else {
            Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void init(View view) {
        tv_brands = (TextView) view.findViewById(R.id.tv_brands);
        tv_material = (TextView) view.findViewById(R.id.tv_material);
        tv_washing = (TextView) view.findViewById(R.id.tv_washing);
        tv_productionplace = (TextView) view.findViewById(R.id.tv_productionplace);
        tv_remark = (TextView) view.findViewById(R.id.tv_remark);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        tv_go_store = (TextView) view.findViewById(R.id.tv_go_store);
        iv_commdetail = (ImageView) view.findViewById(R.id.iv_commdetail);
        imageLoader = ImageLoader.build(getActivity());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_go_store://进入森马

                Intent intent = new Intent(getActivity(), StoreActivity.class);
                intent.putExtra("storeId", commDetail.getStoreId()+"");
                intent.putExtra("brands",commDetail.getBrands());
                startActivity(intent);
                break;

            case R.id.iv_commdetail://商品详情大图
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), CommScaleImageActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//切换无动画效果
                startActivity(intent1);
                break;
        }


    }

}
