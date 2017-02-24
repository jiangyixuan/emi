package com.jyx.emi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.SmallSort;
import com.jyx.emi.loader.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/10.
 */
public class SortSmallAdapter extends BaseAdapter {

    private ImageLoader imageLoader;
    private LayoutInflater inflater;
    private Map<String, ArrayList<SmallSort>> sortDataMap;
    private List<SmallSort> smallSortList;//存放GridView小分类对象

    public SortSmallAdapter(Context context,List<SmallSort> smallSortList) {
        imageLoader = ImageLoader.build(context);
        inflater = LayoutInflater.from(context);
        this.smallSortList=smallSortList;

    }

    /**
     * 更新当前adapter
     * @param smallSortList
     */
    public void notifyDataSetChanged(List<SmallSort> smallSortList)
    {
        this.smallSortList=smallSortList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return smallSortList.size();
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

        //通过LayoutInflater实例化布局
        View view = inflater.inflate(R.layout.gridview_item_smallsort, null);

        ImageView iv_item_sort_img = (ImageView) view.findViewById(R.id.iv_item_sort_img);
        TextView tv_item_sort_name = (TextView) view.findViewById(R.id.tv_item_sort_name);

        //设置 数据
        tv_item_sort_name.setText(smallSortList.get(position).getSmallSortName());

        imageLoader.bindBitmap(smallSortList.get(position).getImg(), iv_item_sort_img, 120, 120);

        return view;
    }
}
