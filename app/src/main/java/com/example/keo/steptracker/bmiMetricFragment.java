package com.example.keo.steptracker;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class bmiMetricFragment extends Fragment
{
    Button btnCalc, btnClear;
    EditText etWeight, etHeight;
    TextView tvCategory, tvResult;
    ImageView ivInfo;

    public bmiMetricFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_bmi_metric, container, false);

        btnCalc = v.findViewById(R.id.btnMetricCalculate);
        btnClear = v.findViewById(R.id.btnMetricClear);

        etWeight = v.findViewById(R.id.etMetricWeight);
        etHeight = v.findViewById(R.id.etMetricHeight);

        tvResult = v.findViewById(R.id.tvMetricResult);
        tvCategory = v.findViewById(R.id.tvMetricCategory);

        ivInfo = v.findViewById(R.id.ivMetricBmiInfo);

        btnCalc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                double weight = Double.parseDouble(etWeight.getText().toString());
                double height = Double.parseDouble(etHeight.getText().toString());

                //BMI Formula used for calculation
                double myBMI = (weight / (height * height));

                //Rounds off to 2 decimal places
                String results = String.format("%.2f", myBMI);

                //Displays BMI
                tvResult.setText(results);

                //Displays BMI Category
                if(myBMI < 18.6)
                {
                    tvCategory.setText("Underweight :(");
                }
                else if(myBMI >= 18.6 && myBMI < 25)
                {
                    tvCategory.setText("Healthy :)");
                }
                else if(myBMI >= 25 && myBMI < 30)
                {
                    tvCategory.setText("Overweight :|");
                }
                else if(myBMI > 30)
                {
                    tvCategory.setText("Obese :(");
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //clears the text
                etWeight.setText("");
                etHeight.setText("");

                //resets the TextViews
                tvCategory.setText("BMI Category");
                tvResult.setText("Result");
            }
        });

        ivInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                moreOnBMI();
            }
        });

        return v;
    }

    public void moreOnBMI()
    {
        Intent info = new Intent(getActivity(), bmiInfo.class);
        startActivity(info);
    }
}
