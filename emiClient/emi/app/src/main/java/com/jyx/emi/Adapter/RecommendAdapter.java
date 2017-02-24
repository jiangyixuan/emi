package com.jyx.emi.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyx.emi.R;
import com.jyx.emi.bean.RecommendStoreOverview;
import com.jyx.emi.io.ACache;
import com.jyx.emi.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RecommendAdapter extends BaseAdapter{

    private final static int RECOMMENDNUM=4;
    private List<RecommendStoreOverview> list;
    private Context context;
    private boolean isAll;
    private ImageLoader imageLoader;

    public RecommendAdapter(Context context,boolean isAll)
    {
        ACache cache= ACache.get(context);
        //解析数据，来自缓存
        this.list=new Gson().fromJson(cache.getAsString("recommendStore"),new TypeToken<ArrayList<RecommendStoreOverview>>(){}.getType());
        this.context=context;
        this.isAll=isAll;
        if (imageLoader == null) {
            imageLoader = ImageLoader.build(context);
        }
    }

    @Override
    public int getCount() {

        if(list!=null)
        {
            if(isAll) {
                return list.size();
            }
            else {
                return RECOMMENDNUM;
            }
        }
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
        View view = View.inflate(context,R.layout.gridview_item_recommend,null);
        ImageView iv_store_img= (ImageView) view.findViewById(R.id.iv_store_img);
        TextView tv_store_name= (TextView) view.findViewById(R.id.tv_store_name);

        if(list!=null) {
            tv_store_name.setText(list.get(position).getStoreName());
            imageLoader.bindBitmap(list.get(position).getStoreImg(), iv_store_img, 100, 100);
        }
        return view;
    }
}
