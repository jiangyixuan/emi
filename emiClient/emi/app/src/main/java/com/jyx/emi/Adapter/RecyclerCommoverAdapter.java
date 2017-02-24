package com.jyx.emi.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.CommOverview;
import com.jyx.emi.loader.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8.
 */
public class RecyclerCommoverAdapter extends RecyclerView.Adapter<RecyclerCommoverAdapter.ViewHolder> {

    protected LayoutInflater mInflater;
    protected Context mContext;
    private List<CommOverview> commOverviews;
    private ImageLoader imageLoader;

    public RecyclerCommoverAdapter(Context context,List<CommOverview> commOverviews) {
        this.commOverviews = commOverviews;
        this.mContext=context;
        mInflater=LayoutInflater.from(context);
        imageLoader=ImageLoader.build(context);

    }

    public OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);

        void onItemLongClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gridview_item_commoverview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

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
        setUpItemEvent(holder);

    }

    @Override
    public int getItemCount() {
        return commOverviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_comm_img;
        public TextView tv_now_price;
        public TextView tv_old_price;
        public TextView tv_discount;
        public TextView tv_comm_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_comm_img= (ImageView) itemView.findViewById(R.id.iv_comm_img);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_now_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);
            tv_comm_name = (TextView) itemView.findViewById(R.id.tv_comm_name);
        }

    }

    protected void setUpItemEvent(final ViewHolder holder)
    {
        if (itemClickListener != null) {

            holder.iv_comm_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition=holder.getLayoutPosition();
                    itemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.iv_comm_img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition=holder.getLayoutPosition();
                    itemClickListener.onItemLongClick(holder.itemView, layoutPosition);
                    return false;
                }
            });
        }
    }

}
