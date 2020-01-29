package com.example.keo.steptracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity
{

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button btnRegister;
    TextView tvLoginHere;
    EditText etName, etSurname, etEmail,etDob, etPassword, etConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);
        tvLoginHere = findViewById(R.id.tvLoginHere);

        clearFields(); //clears fields

        btnRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerPressed();
            }
        });

        tvLoginHere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openLogin();
            }
        });
    }

    public void registerPressed()
    {
        db = openHelper.getWritableDatabase();
        String fname = etName.getText().toString();
        String lname = etSurname.getText().toString();
        String email = etEmail.getText().toString();
        String dob = etDob.getText().toString();
        String pass1 = etPassword.getText().toString();
        String pass2 = etConfirmPass.getText().toString();

        //executes the insertData method
        insertData(fname, lname, email, dob, pass1,pass2);
    }

    public void openLogin()
    {
        Intent login = new Intent(this, Login.class);
        startActivity(login);
    }

    public void insertData(String fname, String lname, String email, String dob, String pass1, String pass2)
    {
        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || dob.isEmpty() || pass1.isEmpty() || pass2.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

        else if(!pass1.equals(pass2))
        {
            Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();

            //Clears the password text
            etPassword.setText("");
            etConfirmPass.setText("");
        }
        else
        {
            //inserts the data into the database
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_NAME, fname);
            contentValues.put(DatabaseHelper.COLUMN_Surname, lname);
            contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
            contentValues.put(DatabaseHelper.COLUMN_DOB, dob);
            contentValues.put(DatabaseHelper.COLUMN_PASSWORD, pass1);

            long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

            Toast.makeText(getApplicationContext(), "Register Successful!", Toast.LENGTH_SHORT).show();

            Intent register = new Intent(this, Login.class);
            startActivity(register);

            //closes the database
            db.close();
        }
    }
    public void clearFields()
    {
        Account.setWeight("");
        Account.setHeight("");
        Account.setSteps_goal("N/A");
        Account.setWeight_goal("N/A");
    }
}
