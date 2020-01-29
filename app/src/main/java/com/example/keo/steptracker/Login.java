package com.example.keo.steptracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
{
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;

    Button btnLogin;
    TextView tvRegisterHere;
    EditText etName, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();

        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterHere = findViewById(R.id.tvRegisterHere);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                loginPressed();
            }
        });

        tvRegisterHere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openRegister();
            }
        });
    }

    public void loginPressed()
    {
        //stores the entered username and password
        String name = etName.getText().toString();
        String pass = etPassword.getText().toString();

        db = openHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE "  + DatabaseHelper.COLUMN_NAME + " =? AND " +DatabaseHelper.COLUMN_PASSWORD + " =? ", new String[]{name,pass});
        
        if(cursor != null)
        {
            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();

                while(!cursor.isAfterLast())
                {
                    //sends values to the set/get class for later use
                    Account.setName(name);

                    if(cursor.getString(cursor.getColumnIndex("id")) != null)
                    {
                        Account.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    }
                    if(cursor.getString(cursor.getColumnIndex("surname")) != null)
                    {
                        Account.setSurname(cursor.getString(cursor.getColumnIndex("surname")));
                    }
                    if(cursor.getString(cursor.getColumnIndex("email")) != null)
                    {
                        Account.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                    }
                    if(cursor.getString(cursor.getColumnIndex("dob")) != null)
                    {
                        Account.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                    }

                    if(cursor.getString(cursor.getColumnIndex("weight")) != null)
                    {
                        Account.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                    }
                    if(cursor.getString(cursor.getColumnIndex("height")) != null)
                    {
                        Account.setHeight(cursor.getString(cursor.getColumnIndex("height")));
                    }

                    if(cursor.getString(cursor.getColumnIndex("weight_goal")) != null)
                    {
                        Account.setWeight_goal(cursor.getString(cursor.getColumnIndex("weight_goal")));
                    }

                    if(cursor.getString(cursor.getColumnIndex("steps_goal")) != null)
                    {
                        Account.setSteps_goal(cursor.getString(cursor.getColumnIndex("steps_goal")));
                    }


                    cursor.moveToNext();
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                }

                //opens next activity when login is successful
                Intent loggedIn= new Intent(this, MainActivity.class);
                loggedIn.putExtra("name", name); //value being passed
                loggedIn.putExtra("pass", pass);
                startActivity(loggedIn);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();

                //Clears the username and password text
                etName.setText("");
                etPassword.setText("");
            }
        }
    }

    public void openRegister()
    {
        Intent openReg = new Intent(this, Register.class);
        startActivity(openReg);
    }
}
