package com.example.keo.steptracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity 
{

    private BottomNavigationView myMainNav;
    private FrameLayout myMainFrame;

    private HomeFragment homeFragment;
    private StatsFragment statsFragment;
    private ProfileFragment profileFragment;
    private GoalsFragment goalsFragment;
    private HistoryFragment historyFragment;
    SharedPreferences getData;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myMainNav = findViewById(R.id.main_nav);
        myMainFrame = findViewById(R.id.main_frame);

        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        goalsFragment = new GoalsFragment();
        statsFragment = new StatsFragment();
        historyFragment = new HistoryFragment();

        //opens the Home fragment by default
        setFragment(homeFragment);

        myMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.navHome:
                        myMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case R.id.navStats:
                        myMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(statsFragment);
                        return true;

                    case R.id.navGoals:
                        myMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(goalsFragment);
                        return true;

                    case R.id.navProfile:
                        myMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(profileFragment);
                        return true;

                    case R.id.navHistory:
                        myMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(historyFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
        fragTrans.replace(R.id.main_frame, fragment);
        fragTrans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.settings:
                settings();
                break;

            case R.id.logout:
                //clears these fields when user logs out
                Account.setWeight("");
                Account.setHeight("");
                Account.setSteps_goal("N/A");
                Account.setWeight_goal("N/A");

                Intent logout = new Intent(this, Login.class);
                startActivity(logout);
                break;
        }
        getSettings();
        return true;
    }

    public void settings()
    {
        Intent settings = new Intent(this, Settings.class);
        startActivity(settings);
    }

    public void getSettings()
    {
        getData = getSharedPreferences("settingsPrefs", Context.MODE_PRIVATE);

        String colorText = getData.getString("color_key", "Imperial");
    }
}
