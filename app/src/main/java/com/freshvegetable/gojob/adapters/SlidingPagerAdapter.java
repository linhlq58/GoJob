package com.freshvegetable.gojob.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.freshvegetable.gojob.fragments.CategoryFragment;
import com.freshvegetable.gojob.fragments.EndingSoonFragment;
import com.freshvegetable.gojob.fragments.NewestFragment;

/**
 * Created by Nam on 8/15/2016.
 */
public class SlidingPagerAdapter extends FragmentPagerAdapter {

    public SlidingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CategoryFragment();
            case 1:
                return new NewestFragment();
            case 2:
                return new EndingSoonFragment();
        }
        return new Fragment();
    }

    private String[] titles = {
            "CATEGORY",
            "NEWEST",
            "ENDING SOON",
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
