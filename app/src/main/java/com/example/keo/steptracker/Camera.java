package com.example.keo.steptracker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Camera extends AppCompatActivity
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button btnCamera, btnSave;
    ImageView cameraImageView;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        btnCamera = findViewById(R.id.btnLaunchCamera);
        btnSave = findViewById(R.id.btnSaveImage);
        cameraImageView = findViewById(R.id.ivCamera);

        //adds back button on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //disables the buttons if the user has no camera
        if(!hasCamera())
        {
            btnCamera.setEnabled(false);
            btnSave.setEnabled(false);

            Toast.makeText(getApplicationContext(), "Camera Unavailable ", Toast.LENGTH_SHORT).show();
        }

        btnCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //opens the camera
                launchCamera();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    //launching the camera
    public void launchCamera()
    {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Takes a picture and passes the results to the onActivityResult(below method)
        startActivityForResult(camera, 0);
    }

    //return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //retrieves the data
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        cameraImageView.setImageBitmap(bitmap);
    }


    //return to previous activity
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
