package com.jyx.emi.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.SearchComm;
import com.jyx.emi.loader.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class SearchCommAdapter extends RecyclerView.Adapter<SearchCommAdapter.ViewHolder>{

    protected LayoutInflater mInflater;
    protected Context mContext;
    private List<SearchComm> searchCommList;
    private ImageLoader imageLoader;

    public SearchCommAdapter(Context context,List<SearchComm> searchCommList) {
        this.searchCommList = searchCommList;
        this.mContext=context;
        mInflater= LayoutInflater.from(context);
        imageLoader= ImageLoader.build(context);

    }

    public OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.search_comm_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tv_comm_name.setText(searchCommList.get(position).getCommName());
        holder.tv_now_price.setText("￥" + searchCommList.get(position).getNowPrice());
        holder.tv_store_neme.setText(searchCommList.get(position).getStoreName());

        ImageView comm_img = holder.iv_comm_img;
        imageLoader.bindBitmap(searchCommList.get(position).getCommImg(), comm_img, 200, 160);
        setUpItemEvent(holder);
    }

    @Override
    public int getItemCount() {
        return searchCommList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv_comm_img;
        public TextView tv_now_price;
        public TextView tv_comm_name;
        public TextView tv_store_neme;
        public LinearLayout ll_item;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_comm_img= (ImageView) itemView.findViewById(R.id.iv_comm_img);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_now_price);
            tv_comm_name = (TextView) itemView.findViewById(R.id.tv_comm_name);
            tv_store_neme= (TextView) itemView.findViewById(R.id.tv_store_name);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }

    }

    protected void setUpItemEvent(final ViewHolder holder)
    {
        if (itemClickListener != null) {

            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    itemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
        }
    }

}
