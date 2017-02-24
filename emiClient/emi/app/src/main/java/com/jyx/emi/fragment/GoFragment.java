package com.jyx.emi.fragment;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.SortAdapter;
import com.jyx.emi.R;
import com.jyx.emi.baidumap.LocationHelper;
import com.jyx.emi.bean.SmallSort;
import com.jyx.emi.bean.StoreMap;
import com.jyx.emi.global.GlobalContants;
import com.jyx.emi.io.ACache;
import com.jyx.emi.utils.HttpUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/24.
 */
public class GoFragment extends MapBaseFragment implements OnClickListener {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgressDialog.dismiss();
            switch (msg.what) {
                case 0://显示地图数据
                    if (storeMaps == null) {
                        Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    showMapData(storeMaps);
                    break;
                default:
                    break;
            }
        }
    };

    private ImageView iv_menu;
    private ImageView iv_search;
    private EditText et_search;
    private PopupWindow popupWindow;
    private LinkedList<String> linkedList;//存放ListView大分类名
    private SortAdapter sortAdapter;
    private List<StoreMap> storeMaps;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        initManager();
        View view = inflater.inflate(R.layout.fragment_go, null);
        init(view);
        initData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 初始化数据
     */
    private void initData() {

        //执行定位
        LocationHelper.initLocation(getActivity(), new MyLocationListener(), baiduMap, mLocationClient);

        requestDataFromServer("flag=all&value=女装");

        //将缓存中的字符串解析成map对象
        ACache cache = ACache.get(getActivity().getApplication());
        String result = cache.getAsString("home_sort");
        //假入缓存数据为空，显示网络错误页面，return
        if (result != null) {
            //解析数据，来自缓存
            Map<String, ArrayList<SmallSort>> map = new Gson().fromJson(result, new TypeToken<Map<String, List<SmallSort>>>() {
            }.getType());
            if (map != null) {
                //通过Map.entrySet遍历key存放到list中
                linkedList = new LinkedList<String>();
                for (Map.Entry<String, ArrayList<SmallSort>> entry : map.entrySet()) {
                    linkedList.addFirst(entry.getKey());
                }
            }
        }
    }

    private void init(View view) {
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        et_search = (EditText) view.findViewById(R.id.et_search);

        mapView = (MapView) view.findViewById(R.id.mapView);
        baiduMap = mapView.getMap();

        iv_menu.setOnClickListener(this);
        iv_search.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.iv_menu://菜单，弹出分类
                showWindow(v);
                break;
            case R.id.iv_search://搜索
                String searchValue = et_search.getText().toString();
                if (TextUtils.isEmpty(searchValue)) {
                    Toast.makeText(getActivity(), R.string.input_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                requestDataFromServer("flag=search&value=" + et_search.getText().toString());

                break;
            default:
                break;
        }
    }

    /**
     * 显示泡泡窗口
     */
    protected void showWindow(View parent) {

        final ListView listView = new ListView(getActivity());
        popupWindow = new PopupWindow(listView, 200, LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 36);
        sortAdapter = new SortAdapter(getActivity().getApplicationContext(), linkedList);
        listView.setAdapter(sortAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //改变listItem显示
                sortAdapter.changeListView(listView, view);

                //更新地图数据
                requestDataFromServer("flag=all&smallvalue=" + linkedList.get(position));
                //隐藏popupWindow
                popupWindow.dismiss();


            }
        });
    }

    /**
     * 请求网络数据
     */
    private void requestDataFromServer(String params) {
        mProgressDialog = ProgressDialog.show(getActivity(), null, "正在加载...");
        HttpUtils.doPostAsyn(GlobalContants.MAP_STORE_URL, params, new HttpUtils.RequestCallBack() {
            @Override
            public void onSuccess(String result) {
                storeMaps = new Gson().fromJson(result, new TypeToken<List<StoreMap>>() {
                }.getType());
                handler.sendEmptyMessage(0);
            }

        });
    }


}

