package com.jyx.emi.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.AllStoreOverview;
import com.jyx.emi.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class AllStoreAdapter extends BaseAdapter {

    LayoutInflater inflater;
    private ViewHolder holder;
    private List<AllStoreOverview> allStores;
    private ImageLoader imageLoader;


    public AllStoreAdapter(Context context, List<AllStoreOverview> allStores){
        inflater = LayoutInflater.from(context);
        this.allStores=allStores;
        if (imageLoader == null) {
            imageLoader = ImageLoader.build(context);
        }
    }


    @Override
    public int getCount() {
        return allStores.size();
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

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_item_allstore, null);
            holder.iv_store_img = (ImageView) convertView.findViewById(R.id.iv_store_img);
            holder.tv_store_name = (TextView) convertView.findViewById(R.id.tv_store_name);
            holder.tv_index = (TextView) convertView.findViewById(R.id.tv_index);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String currentLetter = allStores.get(position).getPinyin().charAt(0) + "";
        String str = null;
        if (position == 0) {
            str = currentLetter;
        } else {
            String preLetter = allStores.get(position - 1).getPinyin().charAt(0) + "";
            if (!TextUtils.equals(preLetter, currentLetter)) {
                str = currentLetter;
            }
        }
        holder.tv_index.setVisibility(str == null ? View.GONE : View.VISIBLE);
        holder.tv_index.setText(currentLetter);
        holder.tv_store_name.setText(allStores.get(position).getBrands());

        ImageView store_img = holder.iv_store_img;
        imageLoader.bindBitmap(allStores.get(position).getStoreImg(), store_img, 100, 100);

        return convertView;
    }

    public final class ViewHolder {
        public TextView tv_store_name;
        public ImageView iv_store_img;
        public TextView tv_index;
    }
}
