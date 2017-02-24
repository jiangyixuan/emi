package com.jyx.emi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.SmallSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/10.
 */
public class SortAdapter extends BaseAdapter {

    private Context context;
    private Map<String, ArrayList<SmallSort>> sortDataMap;
    private LinkedList<String> linkedList;

    public SortAdapter(Context context, LinkedList<String> linkedList) {
        this.context = context;
        this.linkedList = linkedList;
    }

    @Override
    public int getCount() {
        return linkedList.size();
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

        View view = View.inflate(context, R.layout.listview_item_smallsort, null);
        TextView tv_item_sort_name = (TextView) view.findViewById(R.id.tv_item_sort_name);

        //默认第一个item显示样式
        if (position == 0) {
            View view_line = view.findViewById(R.id.view_line);
            view_line.setVisibility(View.VISIBLE);
            tv_item_sort_name.setBackgroundColor(Color.WHITE);
            tv_item_sort_name.setTextColor(Color.BLACK);
        }
        tv_item_sort_name.setText(linkedList.get(position));

        return view;
    }

    /**
     * 改变listItem显示
     */
    public void changeListView(ListView listView,View view) {
        //将listview所有条目置为原来状态
        for (int i = 0; i < listView.getChildCount(); i++) {
            View chileView = listView.getChildAt(i);
            chileView.findViewById(R.id.view_line).setVisibility(View.INVISIBLE);
            TextView tv_item_sort_name = (TextView) chileView.findViewById(R.id.tv_item_sort_name);
            tv_item_sort_name.setBackgroundResource(R.color.sort_ll_bag);
            tv_item_sort_name.setTextColor(Color.GRAY);
        }

        //listview点击条目置为点击后状态
        View view_line = view.findViewById(R.id.view_line);
        view_line.setVisibility(View.VISIBLE);
        TextView tv_item_sort_name = (TextView) view.findViewById(R.id.tv_item_sort_name);
        tv_item_sort_name.setBackgroundResource(R.color.sort_ll_bag_press);
        tv_item_sort_name.setTextColor(Color.BLACK);
    }
}
