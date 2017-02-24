package com.jyx.emi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jyx.emi.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/6/18.
 */
public class SearchHistoryAdapter extends BaseAdapter{

    private List<String> strings;
    protected LayoutInflater mInflater;

    public SearchHistoryAdapter(Context context,List<String> strings) {
        this.strings=strings;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strings.size();
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

        View view = mInflater.inflate(R.layout.list_item_search_history,null);

        TextView tv_search_name= (TextView) view.findViewById(R.id.tv_search_name);
        tv_search_name.setText(strings.get(position)+"");

        return view;
    }
}
