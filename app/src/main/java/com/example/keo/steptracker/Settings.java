package com.example.keo.steptracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity
{
    TextView settings;
    RadioGroup radioGroupColor;
    RadioButton radUnits;
    SharedPreferences prefs;
    SharedPreferences getData;
    Button btnSaveSettings;

    String unitType = "test";
    String colorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //adds back button on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSaveSettings = findViewById(R.id.btnSettingsSave);
        radioGroupColor = findViewById(R.id.radUnitTypes);

        prefs = getSharedPreferences("settingsPrefs", Context.MODE_PRIVATE);
        getData = getSharedPreferences("settingsPrefs", Context.MODE_PRIVATE);


        //sets the selected unit type (radio button)
        setRadioSettings();

        btnSaveSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedUnitType();
            }
        });

    }

    public void selectedUnitType()
    {
        int selectedRadId = radioGroupColor.getCheckedRadioButtonId();
        radUnits = findViewById(selectedRadId);

        SharedPreferences.Editor editor = prefs.edit();
        String color = radUnits.getText().toString();
        editor.putString("color_key",color);

        //stores the data permanently
        editor.commit();

        //outputs saved settings
        Toast.makeText(getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT).show();
    }

    public void setRadioSettings()
    {
        colorText = getData.getString("color_key", "Metric");

        if(colorText.equals("Imperial"))
        {
            radioGroupColor.check(R.id.radImperial);
        }
        else if(colorText.equals("Metric"))
        {
            radioGroupColor.check(R.id.radMetric);
        }
    }

    //adds the back button <-
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home) //returns to the assigned class
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
