package com.jyx.emi.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jyx.emi.R;
import com.jyx.emi.bean.CommOverview;
import com.jyx.emi.loader.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6.
 */
public class CommoverAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ViewHolder holder;
    private Drawable defaultBitmapDrawable;
    private List<CommOverview> commOverviews;

    private ImageLoader imageLoader;

    public CommoverAdapter(Context context, List<CommOverview> commOverviews) {
        this.commOverviews = commOverviews;
        inflater = LayoutInflater.from(context);
        this.defaultBitmapDrawable = context.getResources().getDrawable(R.mipmap.image_default);
        if (imageLoader == null) {
            imageLoader = ImageLoader.build(context);
        }
    }

    @Override
    public int getCount() {
        return commOverviews.size();
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
            convertView = inflater.inflate(R.layout.gridview_item_commoverview, null);
            holder.iv_comm_img = (ImageView) convertView.findViewById(R.id.iv_comm_img);
            holder.tv_now_price = (TextView) convertView.findViewById(R.id.tv_now_price);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
            holder.tv_discount = (TextView) convertView.findViewById(R.id.tv_discount);
            holder.tv_comm_name = (TextView) convertView.findViewById(R.id.tv_comm_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String now_price = commOverviews.get(position).getNowPrice();
        String old_price = commOverviews.get(position).getOldPrice();
        holder.tv_now_price.setText("￥" + now_price);
        holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//TextView中间加横线
        holder.tv_old_price.setText("￥" + old_price);
        double discount = (Double.parseDouble(now_price) * 10 / Double.parseDouble(old_price));
        holder.tv_discount.setText(new DecimalFormat("######0.0").format(discount) + "折");
        holder.tv_comm_name.setText(commOverviews.get(position).getCommName());

        ImageView comm_img = holder.iv_comm_img;

        imageLoader.bindBitmap(commOverviews.get(position).getCommImg(), comm_img, 360, 440);

        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_comm_img;
        public TextView tv_now_price;
        public TextView tv_old_price;
        public TextView tv_discount;
        public TextView tv_comm_name;
    }

}
