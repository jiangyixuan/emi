package com.jyx.emi.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jyx.emi.Adapter.ImagePagerAdapter;
import com.jyx.emi.R;
import com.jyx.emi.bean.CommCollect;
import com.jyx.emi.bean.CommDetail;
import com.jyx.emi.db.CollectHelper;
import com.jyx.emi.fragment.CommDetailAdvisoryFragment;
import com.jyx.emi.fragment.CommDetailCommInfoFragment;
import com.jyx.emi.fragment.CommDetailTopbarFragment;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.MyUtils;
import com.jyx.emi.utils.PreferencesUtils;
import com.jyx.emi.utils.TransferDataUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class CommDetailActivity extends Activity implements View.OnClickListener {
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            mProgressDialog.dismiss();
            findViewById(R.id.activity_comm_detail).setVisibility(View.VISIBLE);

            switch (msg.what) {
                case 0:
                    if (commDetail != null) {
                        //更新ui
                        fl_topbar.showCountdown(commDetail.getDeadline());
                        viewPager.setAdapter(new ImagePagerAdapter(imageViewList, 0));
                        initDots(imageViewList);
                        fl_advisory.initView(commDetail);

                    } else {
                        Toast.makeText(CommDetailActivity.this, R.string.net_error, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1://该商品详细信息还未更新
                    Toast.makeText(CommDetailActivity.this,R.string.comm_detail_err,Toast.LENGTH_SHORT).show();
                    ((ViewStub)findViewById(R.id.vs_net_error)).setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    private LinearLayout ll_collect;
    private TextView tv_now_price, tv_old_price, tv_discount, tv_comm_name;
    private ProgressDialog mProgressDialog;
    private TextView tv_switch_comminfo, tv_switch_advisory;

    private Fragment mContent;
    private CommDetailCommInfoFragment fl_comminfo;
    private CommDetailAdvisoryFragment fl_advisory;
    private CommDetailTopbarFragment fl_topbar;
    private ViewPager viewPager;
    private LinearLayout ll_dot;
    private ImageLoader imageLoader;
    private String commId;//该商品id
    private CommDetail commDetail;
    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
    private int clickCollect=0;
    /**
     * 上一个页面的位置
     */
    protected int lastPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_detail);
        init();
        initData();
        initView(savedInstanceState);
        listener();

    }

    private void initData() {
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        HttpUtils.doPostAsyn(GlobalContants.COMMDETAIL_URL,"commId="+commId, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result)  {
                commDetail = new Gson().fromJson(result, CommDetail.class);
                if(commDetail==null) {
                    handler.sendEmptyMessage(1);
                    return;
                }
                //将commDetail中的url转换成list<View>
                String imgUrls[] = {commDetail.getImg1(), commDetail.getImg2(), commDetail.getImg3(), commDetail.getImg4()};
                for (int i = 0; i < imgUrls.length; i++) {
                    ImageView iv = new ImageView(CommDetailActivity.this);
                    int screenWidth = MyUtils.getScreenMetrics(CommDetailActivity.this).widthPixels;

                    imageLoader.bindBitmap(GlobalContants.SERVER_URL + imgUrls[i], iv, screenWidth, screenWidth);
                    imageViewList.add(iv);
                }

                //发送消息
                handler.sendEmptyMessage(0);
            }

        });
    }

    private void initView(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String now_price = intent.getStringExtra("nowPrice");
        String old_price = intent.getStringExtra("oldPrice");
        tv_now_price.setText("￥" + now_price);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//TextView中间加横线
        tv_old_price.setText("￥" + old_price);
        tv_comm_name.setText(intent.getStringExtra("commName"));
        double discount = (Double.parseDouble(now_price) * 10 / Double.parseDouble(old_price));
        tv_discount.setText(new DecimalFormat("######0.0").format(discount) + "折");

        if (savedInstanceState != null) {
            mContent = getFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        if (mContent == null) {
            mContent = fl_advisory;
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.fl_commdetail, mContent).commit();
        getFragmentManager().beginTransaction()
                .replace(R.id.fl_topbar, fl_topbar).commit();


        if(PreferencesUtils.getBoolean("isLogin",false,this))
        {
            CommCollect collect=new CollectHelper(this).selectCollect(commId);
            if(collect.getCommId()!=0){
                ((ImageView)ll_collect.getChildAt(0)).setImageResource(R.mipmap.detail_collect_selected);
                ((TextView)ll_collect.getChildAt(1)).setTextColor(getResources().getColor(R.color.main));
            }

        }


    }

    private void init() {
        ll_collect = (LinearLayout) findViewById(R.id.ll_collect);
        tv_now_price = (TextView) findViewById(R.id.tv_now_price);
        tv_old_price = (TextView) findViewById(R.id.tv_old_price);
        tv_discount = (TextView) findViewById(R.id.tv_discount);
        tv_comm_name = (TextView) findViewById(R.id.tv_comm_name);
        tv_switch_comminfo = (TextView) findViewById(R.id.tv_switch_comminfo);
        tv_switch_advisory = (TextView) findViewById(R.id.tv_switch_advisory);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_dot= (LinearLayout) findViewById(R.id.ll_dot);
        imageLoader = ImageLoader.build(this);

        fl_comminfo = new CommDetailCommInfoFragment();
        fl_advisory = new CommDetailAdvisoryFragment();
        fl_topbar = new CommDetailTopbarFragment();

        commId = getIntent().getStringExtra("commId");


    }

    private void listener() {
        tv_switch_comminfo.setOnClickListener(this);
        tv_switch_advisory.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ll_dot.getChildAt(position).setEnabled(true);
                ll_dot.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }


        });

    }

    /**
     * 初始化小圆点
     */
    private void initDots(List list){
        for (int i = 0; i < list.size(); i++) {
            View point = new View(this);
            LayoutParams params = new LayoutParams(10, 10);
            params.leftMargin = 20;
            point.setBackgroundResource(R.drawable.selector_dot);
            if(i==0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            ll_dot.addView(point);
        }
    }

    /***
     * 切换fragment,而不让fragment重新实例化
     */
    private void switchContent(Fragment from, Fragment to) {
        if (mContent != to) {
            mContent = to;

            if (!to.isAdded()) {// 先判断是否被add过
                getFragmentManager().beginTransaction()
                        .hide(from).add(R.id.fl_commdetail, to).commit();// 隐藏当前的fragment，add下一个到Activity中
            } else {
                getFragmentManager().beginTransaction()
                        .hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_switch_comminfo://商品信息
                switchContent(mContent, fl_comminfo);
                tv_switch_advisory.setBackgroundResource(R.drawable.switch_commdetail_selector);
                tv_switch_comminfo.setBackgroundResource(R.drawable.switch_commdetail_normal);
                tv_switch_advisory.setTextColor(Color.BLACK);
                tv_switch_comminfo.setTextColor(Color.WHITE);

                break;
            case R.id.tv_switch_advisory://售后咨询
                switchContent(mContent, fl_advisory);
                tv_switch_comminfo.setBackgroundResource(R.drawable.switch_commdetail_selector);
                tv_switch_advisory.setBackgroundResource(R.drawable.switch_commdetail_normal);
                tv_switch_comminfo.setTextColor(Color.BLACK);
                tv_switch_advisory.setTextColor(Color.WHITE);
                break;
            case R.id.ll_collect://点击收藏按钮

                CollectHelper collectHelper=new CollectHelper(this);
                if(PreferencesUtils.getBoolean("isLogin",false,this))//假如已经登录
                {
                    if(clickCollect%2==0)//收藏
                    {
                        ((ImageView)ll_collect.getChildAt(0)).setImageResource(R.mipmap.detail_collect_selected);
                        ((TextView)ll_collect.getChildAt(1)).setTextColor(getResources().getColor(R.color.main));
                        collectHelper.insertCollect(commId, System.currentTimeMillis() + "");
                        Toast.makeText(this,"收藏成功",Toast.LENGTH_SHORT).show();

                    }
                    else {//取消收藏
                        ((ImageView)ll_collect.getChildAt(0)).setImageResource(R.mipmap.detail_collect_normal);
                        ((TextView)ll_collect.getChildAt(1)).setTextColor(Color.BLACK);
                        collectHelper.deleteCollect(commId);
                        Toast.makeText(this,"取消收藏成功",Toast.LENGTH_SHORT).show();
                    }
                    clickCollect++;
                }
                else{
                    Toast.makeText(this,R.string.notlogin,Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CommDetailActivity.this, LoginActivity.class));
                }

                break;

            default:
                break;
        }
    }


    /**
     * fragment传递数据
     */
    public CommDetail getCommDetail() {
        return commDetail;
    }

}
