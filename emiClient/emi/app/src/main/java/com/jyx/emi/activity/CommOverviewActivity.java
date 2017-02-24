package com.jyx.emi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jyx.emi.R;
import com.jyx.emi.bean.CommOverview;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.io.ACache;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.utils.MyUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 点击小分类进入的Activity
 * Created by Administrator on 2016/5/5.
 */
public class CommOverviewActivity extends Activity implements View.OnClickListener,AbsListView.OnScrollListener{

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    if(list!=null&&list.size()!=0){
                        if (isFirst) {
                            gv_pull_refresh.setAdapter(adapter);
                        }
                        else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),R.string.server_error,Toast.LENGTH_SHORT).show();
                        ((ViewStub)findViewById(R.id.vs_net_error)).setVisibility(View.VISIBLE);

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

    private boolean isPullDown=false;//是否下拉刷新
    private boolean isFirst;
    private int page=1;
    private ImageView iv_back;
    private TextView tv_title;
    private ProgressDialog mProgressDialog;
    private LinearLayout ll_sequence_price;//价格排序
    private LinearLayout ll_sequence_discount;//折扣
    private LinearLayout ll_sequence_brands;//品牌

    private String id;//当前小分类ID

    private List<CommOverview> list;
    private PullToRefreshGridView gv_pull_refresh;

    private CommoverAdapter adapter;
    private int flagPrice=0;//判断第一次还是第二次按,选择按大还是小排序
    private int flagDiscount=0;

    private final static int PRICE_DOWN_SORT=1;
    private final static int PRICE_UP_SORT=2;
    private final static int DISCOUNT_UP_SORT=3;
    private final static int DISCOUNT_DOWN_SORT=4;
    private int current_sort=0;//当前排序方式,默认为0

    private ImageLoader imageLoader;
    private boolean isGridViewIdle = true;
    private int imageWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_overview);
        init();
        initData();
        initView();
        listener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        requestDataFromServer(id, page, current_sort);
    }

    public void init() {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        tv_title= (TextView) findViewById(R.id.tv_title);
        ll_sequence_price= (LinearLayout) findViewById(R.id.ll_sequence_price);
        ll_sequence_discount= (LinearLayout) findViewById(R.id.ll_sequence_discount);
        ll_sequence_brands= (LinearLayout) findViewById(R.id.ll_sequence_brands);
        Intent intent=this.getIntent();
        id=intent.getStringExtra("id");
        tv_title.setText(intent.getStringExtra("name"));
        gv_pull_refresh= (PullToRefreshGridView) findViewById(R.id.gv_pull_refresh);

        ILoadingLayout startLabels = gv_pull_refresh
                .getLoadingLayoutProxy(true,false);
        startLabels.setPullLabel("下拉刷新...");
        startLabels.setRefreshingLabel("放开以刷新...");
        startLabels.setReleaseLabel("正在刷新...");

        ILoadingLayout startLabels2 = gv_pull_refresh
                .getLoadingLayoutProxy(false,true);
        startLabels2.setPullLabel("上拉加载...");
        startLabels2.setRefreshingLabel("放开以加载...");
        startLabels2.setReleaseLabel("正在加载中...");

        imageLoader = ImageLoader.build(this);
        int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        int space = (int)MyUtils.dp2px(this, 20f);
        imageWidth = (screenWidth - space) / 2;

    }
    public void initData(){
        adapter=new CommoverAdapter(this);

        if(ACache.get(this).getAsString("comm_over")==null||ACache.get(this).getAsString("comm_over").equals(""))
        {
            requestDataFromServer(id, page, current_sort);
        }
        else
        {
            mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
            list = new Gson().fromJson(ACache.get(this).getAsString("comm_over"), new TypeToken<ArrayList<CommOverview>>() {}.getType());
            if(list==null)
            {
                Toast.makeText(getApplicationContext(),R.string.server_error,Toast.LENGTH_SHORT).show();
                return;
            }
            mProgressDialog.dismiss();
            gv_pull_refresh.setAdapter(adapter);
        }

        isFirst=true;
    }
    public void initView() {

    }

    public void listener() {
        iv_back.setOnClickListener(this);
        ll_sequence_price.setOnClickListener(this);
        ll_sequence_discount.setOnClickListener(this);
        ll_sequence_brands.setOnClickListener(this);
        gv_pull_refresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

                isFirst = false;
                isPullDown=true;
                requestDataFromServer(id, page, current_sort);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                isFirst = false;
                requestDataFromServer(id, page++, current_sort);

            }
        });

        gv_pull_refresh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CommOverviewActivity.this, CommDetailActivity.class);

                intent.putExtra("commId", list.get(position).getCommId() + "");
                intent.putExtra("commName", list.get(position).getCommName());
                intent.putExtra("nowPrice", list.get(position).getNowPrice());
                intent.putExtra("oldPrice", list.get(position).getOldPrice());
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_sequence_price:
                //点击后改变字体图片颜色及图案
                ((TextView)ll_sequence_price.getChildAt(0))
                        .setTextColor(getResources().getColorStateList(R.color.main));
                ((TextView)ll_sequence_discount.getChildAt(0))
                        .setTextColor(Color.GRAY);
                ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(0)))
                        .setImageResource(R.mipmap.icon_open_small_up);
                ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(1)))
                        .setImageResource(R.mipmap.icon_open_small);


                if(flagPrice%2!=0)//按价格上升排序
                {
                    ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(0)))
                            .setImageResource(R.mipmap.icon_open_small_blue_up);
                    ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(1)))
                            .setImageResource(R.mipmap.icon_open_small_press);
                    current_sort=PRICE_UP_SORT;//改变当前排序方式

                }
                else//按价格下降排序
                {
                    ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(0)))
                            .setImageResource(R.mipmap.icon_open_small_up_press);
                    ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(1)))
                            .setImageResource(R.mipmap.icon_open_small_blue);
                    current_sort=PRICE_DOWN_SORT;

                }
                flagDiscount=0;
                flagPrice++;
                requestDataFromServer(id,page,current_sort);

                break;
            case R.id.ll_sequence_discount:
                //点击后改变字体图片颜色及图案
                ((TextView)ll_sequence_discount.getChildAt(0))
                        .setTextColor(getResources().getColorStateList(R.color.main));
                ((TextView)ll_sequence_price.getChildAt(0))
                        .setTextColor(Color.GRAY);
                ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(0)))
                        .setImageResource(R.mipmap.icon_open_small_up);
                ((ImageView)(((LinearLayout)ll_sequence_price.getChildAt(1)).getChildAt(1)))
                        .setImageResource(R.mipmap.icon_open_small);

                if(flagDiscount%2==0)//按折扣上升排序
                {
                    ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(0)))
                            .setImageResource(R.mipmap.icon_open_small_up_press);
                    ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(1)))
                            .setImageResource(R.mipmap.icon_open_small_blue);

                    current_sort=DISCOUNT_UP_SORT;

                }
                else//按折扣下降排序
                {
                    ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(0)))
                            .setImageResource(R.mipmap.icon_open_small_blue_up);
                    ((ImageView)(((LinearLayout)ll_sequence_discount.getChildAt(1)).getChildAt(1)))
                            .setImageResource(R.mipmap.icon_open_small_press);

                    current_sort=DISCOUNT_DOWN_SORT;

                }
                flagPrice=0;
                flagDiscount++;
                requestDataFromServer(id,page,current_sort);

                break;
            case R.id.ll_sequence_brands:
                Intent intent=new Intent(CommOverviewActivity.this,BrandsActivity.class);
                intent.putExtra("smallSortId", id);
                startActivity(intent);
                break;

            default:
                break;

        }
    }

    class CommoverAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;
        private ViewHolder holder;
        private Drawable defaultBitmapDrawable;

        private CommoverAdapter(Context context)
        {
            inflater=LayoutInflater.from(getApplicationContext());
            this.defaultBitmapDrawable=context.getResources().getDrawable(R.mipmap.image_default);
        }

        @Override
        public int getCount() {
            if(list!=null)
            {
                return list.size();
            }
            Toast.makeText(getApplicationContext(),R.string.net_error,Toast.LENGTH_SHORT).show();
            return 0;
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

            if(convertView==null) {
                holder=new ViewHolder();
                convertView=inflater.inflate(R.layout.gridview_item_commoverview,null);
                holder.iv_comm_img= (ImageView) convertView.findViewById(R.id.iv_comm_img);
                holder.tv_now_price= (TextView) convertView.findViewById(R.id.tv_now_price);
                holder.tv_old_price= (TextView) convertView.findViewById(R.id.tv_old_price);
                holder.tv_discount= (TextView) convertView.findViewById(R.id.tv_discount);
                holder.tv_comm_name= (TextView) convertView.findViewById(R.id.tv_comm_name);

                convertView.setTag(holder);
            }
            else {
                holder= (ViewHolder) convertView.getTag();
        }
            String now_price=list.get(position).getNowPrice();
            String old_price=list.get(position).getOldPrice();
            holder.tv_now_price.setText("￥" + now_price);
            holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//TextView中间加横线
            holder.tv_old_price.setText("￥" + old_price);
            double discount=  (Double.parseDouble(now_price)*10/Double.parseDouble(old_price));
            holder.tv_discount.setText(new DecimalFormat("######0.0").format(discount)+"折");
            holder.tv_comm_name.setText(list.get(position).getCommName());

            ImageView comm_img = holder.iv_comm_img;
            final String tag = (String)comm_img.getTag();

            if(tag!=null) {
                if(!tag.equals(list.get(position).getCommImg())) {
                    comm_img.setImageDrawable(defaultBitmapDrawable);
                }
            }
            if (isGridViewIdle) {
                comm_img.setTag(list.get(position).getCommImg());
                imageLoader.bindBitmap(list.get(position).getCommImg(), comm_img, 360, 440);
            }

            return convertView;
        }

        public final class ViewHolder{
            public ImageView iv_comm_img;
            public TextView tv_now_price;
            public TextView tv_old_price;
            public TextView tv_discount;
            public TextView tv_comm_name;
        }

    }

    /**
     * 获取数据
     * @param id 商品分类id
     * @param page GridView显示前几页
     * @param sortby 排序方式
     */
    private void requestDataFromServer(String id,int page,int sortby)
    {
        if(!isPullDown) {
            mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        }
        isPullDown=false;
        HttpUtils.doPostAsyn(GlobalContants.COMM_OVERVIEW_URL,
                "id=" + id + "&page=" + page + "&" + "sortby=" + sortby, new HttpUtils.RequestCallBack() {
                    @Override
                    public void onSuccess(String result) {

                        ACache.get(CommOverviewActivity.this).put("comm_over", result, 5 * ACache.TIME_DAY);
                        list = new Gson().fromJson(result, new TypeToken<ArrayList<CommOverview>>() {
                        }.getType());

                        handler.sendEmptyMessage(0);
                    }

                });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            isGridViewIdle = true;
            if(list!=null){
                adapter.notifyDataSetChanged();
            }
        } else {
            isGridViewIdle = false;
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

}
