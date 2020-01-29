package com.example.keo.steptracker;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {
    EditText etSteps, etWeight;
    Button btnSetGoals;
    TextView tvSteps, tvWeight, tvUnits;

    Cursor cursor;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    //SharedPreferences
    SharedPreferences getData;

    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        etSteps = v.findViewById(R.id.etStepsGoal);
        etWeight = v.findViewById(R.id.etWeightGoal);
        tvWeight = v.findViewById(R.id.tvWeight);
        tvSteps = v.findViewById(R.id.tvSteps);
        tvUnits = v.findViewById(R.id.tvUnitType);
        btnSetGoals = v.findViewById(R.id.btnSetGoals);

        //displays current goals
        currentGoals();

        btnSetGoals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setGoals();
            }
        });
        return v;
    }

    public void currentGoals()
    {
        //getData = getActivity().getSharedPreferences("settingsPrefs",Context.MODE_PRIVATE);
        //String units = getData.getString("color_key", "Imperial");

        //outputs the current goals
        if(Account.getSteps_goal() == null && Account.getWeight_goal() == null)
        {
            tvSteps.setText("N/A");
            tvWeight.setText("N/A");
        }
        else
        {
            tvSteps.setText(Account.getSteps_goal());
            tvWeight.setText(Account.getWeight_goal());
        }


        /*if(units.equals("Imperial"))
        {
            tvUnits.setText("lbs");

            double kg = Double.valueOf(Account.getWeight_goal());
            double pounds = 2.260462 * kg;
            String lbs = String.valueOf(pounds);
            // String lbsRounded = String.format(Locale.getDefault(),"%.2f", lbs);
            Account.setWeight_goal(lbs); //sets new weight in lbs


        }
        else if(units.equals("Metric"))
        {
            tvUnits.setText("kg");

            double pounds = Double.valueOf(Account.getWeight_goal());
            double kilogram = 0.453592 * pounds;
            String kg = String.valueOf(kilogram);
            //String kgRounded = String.format(Locale.getDefault(),"%.2f", kg);
            Account.setWeight_goal(kg); //sets new weight in kgs

            //outputs the current goals
            tvSteps.setText(Account.getSteps_goal());
            tvWeight.setText(Account.getWeight_goal());
        }*/
    }

    public void setGoals()
    {
        String weight = etWeight.getText().toString();
        String steps = etSteps.getText().toString();

        if (weight.isEmpty() || steps.isEmpty())
        {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                openHelper = new DatabaseHelper(getActivity());
                db = openHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_WEIGHT_GOAL, weight);
                values.put(DatabaseHelper.COLUMN_STEPS_GOAL, steps);

                //updates steps and weight in account
                Account.setSteps_goal(steps);
                Account.setWeight_goal(weight);

                long id = db.update(DatabaseHelper.TABLE_NAME, values,  DatabaseHelper.COLUMN_ID + " = " + Account.getId(), null);

                Toast.makeText(getActivity(), "Goals Saved!", Toast.LENGTH_SHORT).show();
            }
            catch (SQLException e)
            {
                Toast.makeText(getActivity(), "error " + e, Toast.LENGTH_LONG).show();
            }

            //displays the current goals
            tvSteps.setText(steps);
            tvWeight.setText(weight);

            //clear the text
            etWeight.setText("");
            etSteps.setText("");
        }
    }
}