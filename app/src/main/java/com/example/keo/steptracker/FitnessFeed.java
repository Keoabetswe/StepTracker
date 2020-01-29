package com.example.keo.steptracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class FitnessFeed extends AppCompatActivity
{
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_feed);


        display = findViewById(R.id.tvFeedDisplay);

        //adds back button on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StringBuilder builder = new StringBuilder();

        String message = "Recommended levels of physical activity for adults aged 18 - 64 years\n\n" +

                "1.Adults aged 18–64 should do at least 150 minutes of moderate-intensity aerobic physical" +
                "activity throughout the week or do at least 75 minutes of vigorous-intensity aerobic" +
                "physical activity throughout the week or an equivalent combination of moderate- and vigorous-intensity activity.\n\n" +

                "2.Aerobic activity should be performed in sets of at least 10 minute durations.\n\n" +

                "3.For additional health benefits, adults should increase their moderate-intensity aerobic physical" +
                "activity to 300 minutes per week, or engage in 150 minutes of vigorous-intensity aerobic physical " +
                "activity per week, or an equivalent combination of moderate- and vigorous-intensity activity. \n\n" +

                "4.Muscle-strengthening activities should be done involving major muscle groups on 2 or more days a week.\n\n" +

                "Benefits of physical activity for adults\n\n" +
                "Overall, strong evidence demonstrates that compared to less active adult men and women, \n" +
                "individuals who are more active:\n\n" +

                "- have lower rates of all-cause mortality, coronary heart disease, high blood pressure, " +
                "stroke, type 2 diabetes, metabolic syndrome, colon and breast cancer, and depression;\n\n" +

                "- are likely to have less risk of a hip or vertebral fracture;" +
                "exhibit a higher level of cardirespiratory and muscular fitness; and \n\n" +

                "- are more likely to achieve weight maintenance, have a healthier body mass and composition \n\n" +

                "- Experts say that while 10,000 steps a day is a good number to reach, any amount of activity" +
                " beyond what you're currently doing will likely benefit your health.\n\n" +

                "There are many different opinions on how much water we should be drinking every day." +
                "The health authorities commonly recommend eight 8-ounce glasses, which equals about " +
                "2 liters, or half a gallon. This is called the 8×8 rule and is very easy to remember.";

        builder.append(message);


        display.setText(builder.toString());
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
