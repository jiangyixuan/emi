package com.jyx.emi.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyx.emi.R;
import com.jyx.emi.bean.Posts;

import java.util.List;

/**
 * Created by H_T on 2016/10/3.
 */
public class RecyclerPostsAdapter extends RecyclerView.Adapter<RecyclerPostsAdapter.MyViewHolder>{

    private Context context;
    private List<Posts> lsPosts;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public RecyclerPostsAdapter(Context context, List<Posts> lsPosts){
        this.context = context;
        this.lsPosts = lsPosts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_posts,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvUser.setText(lsPosts.get(position).getName());
        holder.tvTitle.setText(lsPosts.get(position).getTitle());
        holder.tvBody.setText(lsPosts.get(position).getBody());
        holder.tvTime.setText(lsPosts.get(position).getTime());

        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return lsPosts.size();
    }




    class MyViewHolder extends  RecyclerView.ViewHolder{

        public TextView tvUser;
        public TextView tvTitle;
        public TextView tvBody;
        public TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvUser = (TextView) itemView.findViewById(R.id.tv_user);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvBody = (TextView) itemView.findViewById(R.id.tv_body);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
