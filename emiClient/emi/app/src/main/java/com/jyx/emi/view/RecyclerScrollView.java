package com.jyx.emi.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 解决ScrollView与RecyclerView问题
 * Created by Administrator on 2016/6/8.
 */
public class RecyclerScrollView extends RecyclerView{
    public RecyclerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerScrollView(Context context) {
        super(context);
    }

    public RecyclerScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
