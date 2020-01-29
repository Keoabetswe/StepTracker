package com.example.keo.steptracker;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    Button btnSave;
    private static EditText etName, etSurname, etDOB, etEmail, etPassword, etWeight, etHeight;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    DatabaseHelper dbHelper;
    Cursor cursor;


    SharedPreferences getData;

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
         View v = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = v.findViewById(R.id.etProfileName);
        etSurname = v.findViewById(R.id.etProfileSurname);
        etEmail = v.findViewById(R.id.etProfileEmail);
        etDOB = v.findViewById(R.id.etProfileDOB);
        etWeight = v.findViewById(R.id.etProfileWeight);
        etHeight = v.findViewById(R.id.etProfileHeight);
        btnSave = v.findViewById(R.id.btnProfileSave);

        //convert(); //convert height and weight
        Profile(); //displays the users profile

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveProfile();
            }
        });

        return v;
    }

    public void saveProfile()
    {
        String newName = etName.getText().toString();
        String newSurname = etSurname.getText().toString();
        String newEmail = etEmail.getText().toString();
        String newDob = etDOB.getText().toString();
        String newWeight = etWeight.getText().toString();
        String newHeight = etHeight.getText().toString();


        updateProfile(newName, newSurname, newEmail, newDob, newWeight, newHeight);
    }

    public void updateProfile(String newName, String newSurname, String newEmail, String newDob, String newWeight, String newHeight)
    {
        if (newName.isEmpty() || newSurname.isEmpty() || newEmail.isEmpty() || newDob.isEmpty() || newWeight.isEmpty() || newHeight.isEmpty())
        {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            try
            {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_NAME, newName);
                values.put(DatabaseHelper.COLUMN_Surname, newSurname);
                values.put(DatabaseHelper.COLUMN_EMAIL, newEmail);
                values.put(DatabaseHelper.COLUMN_DOB, newDob);
                values.put(DatabaseHelper.COLUMN_WEIGHT, newWeight);
                values.put(DatabaseHelper.COLUMN_HEIGHT, newHeight);

                //updates the profile
                long id = db.update(DatabaseHelper.TABLE_NAME, values,  DatabaseHelper.COLUMN_ID + " = " + Account.getId(), null);

                //logs the weight changes with current date
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:mm:ss");
                String currentDate = sdf.format(new Date());

                ContentValues logValues = new ContentValues();
                logValues.put(DatabaseHelper.HISTORY_DATE, currentDate); //saves current data
                logValues.put(DatabaseHelper.HISTORY_WEIGHT, newWeight); //saves new weight change

                long logId = db.insert(DatabaseHelper.TABLE_HISTORY, null, logValues);

                Toast.makeText(getActivity(), "Profile Updated!", Toast.LENGTH_LONG).show();
            }
            catch (SQLException e)
            {
                Toast.makeText(getActivity(), "error " + e, Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void Profile()
    {
        etName.setText(Account.getName());
        etSurname.setText(Account.getSurname());
        etEmail.setText(Account.getEmail());
        etDOB.setText(Account.getDob());
        etWeight.setText(Account.getWeight());
        etHeight.setText(Account.getHeight());
    }

    public void convert()
    {
        getData = getActivity().getSharedPreferences("settingsPrefs",Context.MODE_PRIVATE);
        String units = getData.getString("color_key", "Imperial");

        if(units.equals("Imperial"))
        {
            double kg = Double.valueOf(Account.getWeight());
            double pounds = 2.260462 * kg;
            String lbs = String.valueOf(pounds);

            Account.setWeight(lbs); //sets new weight in lbs
        }
        else if(units.equals("Metric"))
        {
            double pounds = Double.valueOf(Account.getWeight());
            double kilogram = 0.453592 * pounds;
            String kg = String.valueOf(kilogram);

            Account.setWeight(kg); //sets new weight in kgs
        }
    }
}
