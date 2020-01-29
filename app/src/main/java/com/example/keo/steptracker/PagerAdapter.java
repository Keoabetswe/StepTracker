package com.example.keo.steptracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

public class PagerAdapter extends FragmentStatePagerAdapter
{
    int numTabs;

    public PagerAdapter(FragmentManager fm, int noTabs)
    {
        super(fm);
        this.numTabs = noTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                bmiImperialFragment imperial = new bmiImperialFragment();
                return imperial;

            case 1:
                bmiMetricFragment metric = new bmiMetricFragment();
                return metric;

            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return numTabs;
    }
}
