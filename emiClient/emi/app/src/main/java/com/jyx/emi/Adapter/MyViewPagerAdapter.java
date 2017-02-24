package com.jyx.emi.Adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyViewPagerAdapter extends PagerAdapter
{
	ArrayList<ImageView> mImageViewList;
	
	public MyViewPagerAdapter(ArrayList<ImageView> mImageViewList) {
		// TODO Auto-generated constructor stub
		this.mImageViewList=mImageViewList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		
		container.removeView((View) object);
		
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewGroup) container).addView(mImageViewList.get(position));
		return mImageViewList.get(position);
	}
	
}
