package com.example.keo.steptracker;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class bmiCalculator extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        //adds back button on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //calls the viewpager
        setupViewPager();
    }

    private void setupViewPager()
    {
        //adapter for the viewpager
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //add fragments to the adapter
        adapter.addFragment(new bmiMetricFragment());
        adapter.addFragment(new bmiImperialFragment());

        //init viewpager
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);

        //set adapter
        vp.setAdapter(adapter);

        //init tabl ayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //pass the viewpager to the tab layout
        tabLayout.setupWithViewPager(vp);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(R.color.white_text), getResources().getColor(R.color.white_text));

        //set the tabs index and set text, icon etc for the tabs
        tabLayout.getTabAt(0).setText("Imperial");
        tabLayout.getTabAt(1).setText("Metric");
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList= new ArrayList<>();
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment)
        {
            mFragmentList.add(fragment);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
