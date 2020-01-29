package com.example.keo.steptracker;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SensorEventListener, StepListener
{
    //FAB
    FloatingActionButton fabCamera, fabBMI, fabFeed, fabPlus;
    Animation fab_open, fab_close, fab_rotate_clockwise, fab_rotate_anticlockwise;
    Boolean isOpen = false;
    private TextView tvUsername;

    public StepDetector simpleStepDetector;
    public SensorManager sensorManager;
    public Sensor accel;

    private Chronometer tvActiveTime;
    private boolean running;
    private long pauseOfSet;

    TextView tvDistanceHeader;
    TextView tvCalories;
    TextView tvDistance ;
    TextView tvSteps;
    TextView tvStepsGoals;

    private int numSteps;
    int numCalories;
    double numDistance;

     Button btnStartPause;

     //SharedPreferences
     SharedPreferences getData;

    DatabaseHelper myDB;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //displays logged in user
        tvUsername = v.findViewById(R.id.tvUser);
        String name = getActivity().getIntent().getExtras().getString("name");
        tvUsername.setText(getString(R.string.user) + " " + name);

        //displays counted steps and goal
        tvSteps = v.findViewById(R.id.tvStepCounter);
        tvStepsGoals = v.findViewById(R.id.tvStepsGoals);

        //start/pause buttons
        btnStartPause = v.findViewById(R.id.btnStart);


        //DISPLAYS time, distance and calories burned
        tvActiveTime = v.findViewById(R.id.tvActiveTimeDisplay);
        tvCalories = v.findViewById(R.id.tvCaloriesDisplay);
        tvDistance = v.findViewById(R.id.tvDistanceDisplay);
        tvDistanceHeader = v.findViewById(R.id.tvDistance);

        //Get an instance of the SensorManager
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        //floating action button
        fabPlus = v.findViewById(R.id.fab_main);
        fabCamera = v.findViewById(R.id.fabCamera);
        fabBMI = v.findViewById(R.id.fabBMI);
        fabFeed = v.findViewById(R.id.fabFeed);

        //opens and closes FAB  with animation
        fab_open = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        fab_rotate_clockwise = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_rotate_clockwise);
        fab_rotate_anticlockwise = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_rotate_anticlockwise);

        //displays current users steps goal
        stepsGoals();

        //gets current settings
        getSettings();

        //caling settings method
        Settings settings = new Settings();
        String units = settings.colorText;

        tvDistanceHeader.setText(units);


        //start/pause setOnClick
        btnStartPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (running)
                {
                    pauseCounting();
                }
                else
                {
                    startCounting();
                }
            }
        });

        //FAB setOnClicks
        fabPlus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openFab();
            }
        });
        fabCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openCamera();
            }
        });
        fabBMI.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openBMI();
            }
        });
        fabFeed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openFitnessFeed();
            }
        });

        return v;
    }

    private void startCounting()
    {
        //starts counting steps and active time
        sensorManager.registerListener(HomeFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        if(!running)
        {
            tvActiveTime.setBase(SystemClock.elapsedRealtime() - pauseOfSet);
            tvActiveTime.start();
            running = true;
        }
        btnStartPause.setText("Pause");

        //output when start is pressed
        Toast.makeText(getActivity(), "Counting",Toast.LENGTH_SHORT).show(); ;
    }

    private void pauseCounting()
    {
        //pauses counting steps and active time
        sensorManager.unregisterListener(HomeFragment.this);

        if(running)
        {
            tvActiveTime.stop();
            pauseOfSet = SystemClock.elapsedRealtime() - tvActiveTime.getBase();
            running = false;
        }
        btnStartPause.setText("Start");

        //output when pause is pressed
        Toast.makeText(getActivity(), "Paused Counting",Toast.LENGTH_SHORT).show(); ;
    }



    public void openFab()
    {
        if(isOpen)
        {
            fabBMI.startAnimation(fab_close);
            fabCamera.startAnimation(fab_close);
            fabFeed.startAnimation(fab_close);

            fabPlus.startAnimation(fab_rotate_anticlockwise);

            fabFeed.setClickable(false);
            fabBMI.setClickable(false);
            fabCamera.setClickable(false);

            isOpen = false;
        }
        else
        {
            fabBMI.startAnimation(fab_open);
            fabCamera.startAnimation(fab_open);
            fabFeed.startAnimation(fab_open);

            fabPlus.startAnimation(fab_rotate_clockwise);

            fabFeed.setClickable(true);
            fabBMI.setClickable(true);
            fabCamera.setClickable(true);

            isOpen = true;
        }
    }

    public void openCamera()
    {
        Intent camera = new Intent(getActivity(), Camera.class);
        startActivity(camera);
    }

    public void openBMI()
    {
        Intent bmi = new Intent(getActivity(), bmiCalculator.class);
        startActivity(bmi);
    }

    public void openFitnessFeed()
    {
        Intent feed = new Intent(getActivity(), FitnessFeed.class);
        startActivity(feed);
    }

    public void stepsGoals()
    {
        if(Account.getSteps_goal() == null)
        {
            tvStepsGoals.setText("N/A");
        }
        else
        {
            //outputs the current steps goals
            tvStepsGoals.setText(Account.getSteps_goal());
        }


    }

    @Override
    public void step(long timeNs)
    {
        numSteps++;

        //String results = String.format("%.2f", myBMI);
        // double height = Double.parseDouble(etHeight.getText().toString());
        getData = getActivity().getSharedPreferences("settingsPrefs",Context.MODE_PRIVATE);
        String units = getData.getString("color_key", "Imperial");

        //output calories, 20 steps to burn 1 calorie
        if(numSteps %20 == 0)
        {
            ++numCalories;
            tvCalories.setText("" +numCalories);
        }
        else
        {
            tvCalories.setText("" + numCalories);
        }


        if(units.equals("Metric"))
        {
            ////counts distance in KMs, 328 steps = 0.25km, 1312 steps = 1km
            if(numSteps %328 == 0)
            {
                numDistance += 0.25;  //increments by
                tvDistance.setText("" + numDistance);
            }
            else
            {
                tvDistance.setText("" + numDistance);
            }
        }
        else if(units.equals("Imperial"))
        {
            //counts distance in Miles, 500 steps = 0.25mi, 2000 steps = 1 mile
            if(numSteps %10 == 0)
            {
                numDistance += 0.25;  //increments by
                tvDistance.setText("" + numDistance);
            }
            else
            {
                tvDistance.setText("" + numDistance);
            }
        }

        //output the number of steps
        tvSteps.setText("" + numSteps);

        int myStepsGoal= Integer.parseInt(Account.getSteps_goal());

        if(numSteps == myStepsGoal)
        {
            Toast.makeText(getActivity(), "Steps Goal Achieved!", Toast.LENGTH_LONG).show();
        }

        //saves the stats
        if (!running)
        {
            openHelper = new DatabaseHelper(getActivity());
            db = openHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.STATS_STEPS, numSteps);
            values.put(DatabaseHelper.STATS_CALORIES, numCalories);
            values.put(DatabaseHelper.STATS_DISTANCE, numDistance);


            //updates the profile
            long id = db.insert(DatabaseHelper.TABLE_STATS, null, values);
        }
    }

    public void getSettings()
    {
        getData = getActivity().getSharedPreferences("settingsPrefs",Context.MODE_PRIVATE);
        String units = getData.getString("color_key", "Imperial");

        if(units.equals("Imperial"))
        {
            tvDistanceHeader.setText("Miles");
        }
        else if(units.equals("Metric"))
        {
            tvDistanceHeader.setText("Kilometers");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        getSettings();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
