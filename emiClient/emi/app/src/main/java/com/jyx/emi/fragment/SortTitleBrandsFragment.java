package com.jyx.emi.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.AllStoreAdapter;
import com.jyx.emi.Adapter.RecommendAdapter;
import com.jyx.emi.R;
import com.jyx.emi.activity.AllCommendStoreActivity;
import com.jyx.emi.activity.StoreActivity;
import com.jyx.emi.bean.AllStoreOverview;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.io.ACache;
import com.jyx.emi.utils.HttpUtils;
import com.jyx.emi.view.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SortTitleBrandsFragment extends Fragment implements View.OnClickListener {

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
                case 0://获取推荐店铺数据数据，缓存成功
                    if (gv_recommend_store == null) {
                        Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    gv_recommend_store.setAdapter(new RecommendAdapter(getContext(), false));
                    break;
                case 1://获取全部店铺数据数据，缓存成功
                    allStores = new Gson().fromJson(cache.getAsString("allStore"), new TypeToken<ArrayList<AllStoreOverview>>() {
                    }.getType());
                    allStoreAdapter = new AllStoreAdapter(getActivity(),allStores);
                    if (allStores == null) {
                        Log.i("asdasd","allStores is null...");
                        Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listenerIndex();
                    break;
                default:
                    Log.i("asdasd", "default");
                    break;
            }
        }
    };

    private ACache cache;
    private ProgressDialog mProgressDialog;
    private ViewStub vs_net_error;
    private GridView gv_recommend_store;
    private ListView lv_store;
    private TextView tv_center;
    private QuickIndexBar bar;
    private LinearLayout ll_more;//全部特卖旗舰店
    private ArrayList<AllStoreOverview> allStores;//存储全部店铺名和图片

    private AllStoreAdapter allStoreAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_title_brands, null);

        init(view);
        initData();
        listener();
        return view;
    }

    private void init(View view) {
        gv_recommend_store = (GridView) view.findViewById(R.id.gv_recommend_store);
        vs_net_error= (ViewStub) view.findViewById(R.id.vs_net_error);
        lv_store = (ListView) view.findViewById(R.id.lv_store);
        tv_center = (TextView) view.findViewById(R.id.tv_center);
        bar = (QuickIndexBar) view.findViewById(R.id.bar);
        ll_more = (LinearLayout) view.findViewById(R.id.ll_more);
    }

    private void initData() {
        cache = ACache.get(getActivity());
        mProgressDialog = ProgressDialog.show(getActivity(), null, "正在加载...");
        //假如没有缓存，请求服务器数据设为缓存，设置adapter
        if (cache.getAsString("recommendStore") == null) {
            HttpUtils.doPostAsyn(GlobalContants.RECOMMENDSTORE_URL,"flag="+"all"+"&"+"smallSortId="
                    ,new HttpUtils.RequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    //访问成功，将结果保存至缓存
                    cache.put("recommendStore", result, 5 * ACache.TIME_DAY);
                    //通知主线程setAdapter
                    handler.sendEmptyMessage(0);
                }
            });
        } else {
            allStores = new Gson().fromJson(cache.getAsString("allStore"), new TypeToken<ArrayList<AllStoreOverview>>() {
            }.getType());
            gv_recommend_store.setAdapter(new RecommendAdapter(getContext(), false));
            mProgressDialog.dismiss();
        }

        if (cache.getAsString("allStore") == null) {

            HttpUtils.doGetAsyn(GlobalContants.ALLSTORE_URL, new HttpUtils.RequestCallBack() {
                @Override
                public void onSuccess(String result) {
                    //访问成功，将结果保存至缓存
                    cache.put("allStore", result, 5 * ACache.TIME_DAY);
                    //通知主线程setAdapter
                    handler.sendEmptyMessage(1);

                }

            });
        } else {
            listenerIndex();
        }

    }

    private void listener() {
        ll_more.setOnClickListener(this);
//        listenerIndex();
       lv_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(), StoreActivity.class);
               intent.putExtra("storeId", allStores.get(position).getStoreId()+"");
               intent.putExtra("brands",allStores.get(position).getBrands());
               startActivity(intent);
           }
       });
        gv_recommend_store.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0)
                {
                    Intent intent = new Intent(getActivity(), StoreActivity.class);
                    intent.putExtra("storeId","1");
                    intent.putExtra("brands","森马");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), StoreActivity.class);
                    intent.putExtra("storeId", allStores.get(position).getStoreId()+"");
                    intent.putExtra("brands",allStores.get(position).getBrands());
                    startActivity(intent);
                }

            }
        });
}

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_more:
                startActivity(new Intent(getActivity(), AllCommendStoreActivity.class));
                break;

            default:
                break;
        }

    }

    //对索引进行监听
    private void listenerIndex() {
        if(allStores==null)
        {
            Log.i("asdasd","2");
            Toast.makeText(getActivity(),R.string.net_error,Toast.LENGTH_SHORT).show();
            vs_net_error.setVisibility(View.VISIBLE);
            return;
        }
        bar.setListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                showLetter(letter);
                bar.setBackgroundResource(R.drawable.bg_index);
                //根据字母定位ListView
                //找到集合中第一个以letter为拼音为首字母的对象,得到索引
                for (int i = 0; i < allStores.size(); i++) {
                    AllStoreOverview store = allStores.get(i);
                    String pinyin = store.getPinyin().charAt(0) + "";
                    if (TextUtils.equals(letter, pinyin)) {
                        //匹配成功
//                        lv_store.requestFocusFromTouch();//获取焦点
                        lv_store.setSelection(i);//！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                        break;
                    }
                }

            }

            //显示字母
            private void showLetter(String letter) {
                // TODO Auto-generated method stub
                tv_center.setVisibility(View.VISIBLE);
                tv_center.setText(letter);

                handler.removeCallbacksAndMessages(null);
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        tv_center.setVisibility(View.GONE);
                        bar.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    }
                }, 800);
            }
        });
        try {
            Collections.sort(allStores);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.i("asdasd","123");
        if(allStores!=null) {
            Log.i("asdasd","456");
            lv_store.setAdapter(allStoreAdapter);
        }
        Log.i("asdasd","789");
    }

}
