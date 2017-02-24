package com.jyx.emi.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by H_T on 2016/10/2.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> lsFrag;

    public FragmentAdapter(FragmentManager fm,List<Fragment> lsFrag) {
        super(fm);
        this.lsFrag = lsFrag;
    }

    @Override
    public Fragment getItem(int position) {
        return lsFrag.get(position);
    }

    @Override
    public int getCount() {
        return lsFrag.size();
    }
}
