package com.example.keo.steptracker;


import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment
{
    ListView listview;
    DatabaseHelper myDB;

    public HistoryFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View v =  inflater.inflate(R.layout.fragment_history, container, false);
        listview = v.findViewById(R.id.historyListView);

        try
        {
            myDB = new DatabaseHelper(getActivity());

            //populates an ArrayList from the database for viewing in view list
            ArrayList<String> theList = new ArrayList<>();
            Cursor data = myDB.getWeightLog();

            if(data.getCount() == 0)
            {
                Toast.makeText(getActivity(), "Weight log is empty",Toast.LENGTH_LONG).show();
            }
            else
            {
                while(data.moveToNext())
                {
                    theList.add(data.getString(1));
                    theList.add(data.getString(2));
                    theList.add(data.getString(3));
                    ListAdapter listAdapter = new ArrayAdapter<>(getActivity(),android.R.layout. simple_list_item_1,theList);
                    listview.setAdapter(listAdapter);
                }
            }
        }
        catch (SQLException e)
        {
            Toast.makeText(getActivity(), "error " + e,Toast.LENGTH_LONG).show();
        }

        return v;
    }
}
