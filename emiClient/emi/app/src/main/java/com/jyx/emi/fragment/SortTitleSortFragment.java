package com.jyx.emi.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.Adapter.SortAdapter;
import com.jyx.emi.Adapter.SortSmallAdapter;
import com.jyx.emi.R;
import com.jyx.emi.activity.CommOverviewActivity;
import com.jyx.emi.bean.SmallSort;
import com.jyx.emi.loader.ImageLoader;
import com.jyx.emi.io.ACache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/29.
 */
public class SortTitleSortFragment extends Fragment {

    private ListView lv_big_sort;
    private GridView gv_small_sort;
    private Map<String, ArrayList<SmallSort>> sortDataMap;//存放大分类名和小分类对象
    private LinkedList<String> linkedList;//存放ListView大分类名
    private List<SmallSort> smallSortList;//存放GridView小分类对象
    private SortSmallAdapter sortSmallAdapter;
    private SortAdapter sortAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_title_sort, null);
        init(view);
        initData();
        initView(view);
        listener();
        return view;
    }

    private void init(View view) {
        lv_big_sort = (ListView) view.findViewById(R.id.lv_big_sort);
        gv_small_sort = (GridView) view.findViewById(R.id.gv_small_sort);
    }

    private void initData() {
        //将缓存中的字符串解析成map对象
        ACache cache = ACache.get(getActivity().getApplication());
        String result = cache.getAsString("home_sort");
        if (result != null) {
            //解析数据，来自缓存
            sortDataMap = new Gson().fromJson(result, new TypeToken<Map<String, List<SmallSort>>>() {
            }.getType());
            if (sortDataMap != null) {
                //通过Map.entrySet遍历key存放到list中
                linkedList = new LinkedList<String>();
                for (Map.Entry<String, ArrayList<SmallSort>> entry : sortDataMap.entrySet()) {

                    linkedList.addFirst(entry.getKey());
                }
                smallSortList = sortDataMap.get(linkedList.get(0));
            }
        }

    }

    private void initView(View view) {
        if (sortDataMap == null) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.server_error, Toast.LENGTH_SHORT).show();
            ((ViewStub)view.findViewById(R.id.vs_net_error)).setVisibility(View.VISIBLE);
            return;
        }
        sortAdapter = new SortAdapter(getActivity().getApplicationContext(),linkedList);
        lv_big_sort.setAdapter(sortAdapter);
        sortSmallAdapter = new SortSmallAdapter(getActivity().getApplication(),smallSortList);
        gv_small_sort.setAdapter(sortSmallAdapter);
    }

    private void listener() {
        lv_big_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sortAdapter.changeListView(lv_big_sort,view);

                //更新界面
                smallSortList = sortDataMap.get(linkedList.get(position));
                sortSmallAdapter.notifyDataSetChanged(smallSortList);

            }
        });
        gv_small_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), CommOverviewActivity.class);
                intent.putExtra("id", smallSortList.get(position).getSmallSortId() + "");
                intent.putExtra("name", smallSortList.get(position).getSmallSortName() + "");
                startActivity(intent);
            }
        });
    }


}



