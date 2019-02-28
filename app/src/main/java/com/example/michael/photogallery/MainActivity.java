package com.example.michael.photogallery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.michael.photogallery.utility.Photo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_SEARCH_CODE = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_LOCATION_PERMISSION = 3;

    private String currentPhotoPath = null;
    private int currentPhotoIndex = 0;
    private ArrayList<String> photoGallery;
    private FusedLocationProviderClient mFusedLocationClient;
    private Date startDate;
    private Date endDate;
    private double latitude = 0;
    private double longitude = 0;
    private double[] loc1;
    private double[] loc2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        resetDate();
        resetLocationFilter();
        photoGallery = populateGallery(minDate, maxDate);
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        if (photoGallery.size() > 0)
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
        displayPhoto(currentPhotoPath);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            } else {
                                Log.d("Location Error: ", "Something wrong inside the getLocation()");
                            }
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            "Permission Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private View.OnClickListener filterListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, FilterActivity.class);
            startActivityForResult(i, REQUEST_SEARCH_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH_CODE) {
            if (resultCode == RESULT_OK) {

                try {
                    loc1 = data.getDoubleArrayExtra("STARTLOC");
                    loc2 = data.getDoubleArrayExtra("ENDLOC");
                    fillDate(data);

                    photoGallery = populateGallery(startDate, endDate);
                    resetLocationFilter();
                    resetDate();
                } catch (Exception e) {
                    Log.d("Exception: ", e.toString());
                }

                Log.d("onCreate, size", Integer.toString(photoGallery.size()));
                if (photoGallery.size() > 0) {
                    currentPhotoIndex = 0;
                    currentPhotoPath = photoGallery.get(currentPhotoIndex);
                    displayPhoto(currentPhotoPath);
                } else {
                    currentPhotoIndex = 0;
                    ImageView iv = (ImageView) findViewById(R.id.gallery_picture);
                    iv.setImageResource(R.drawable.logo);
                }

            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Log.d("createImageFile", "Picture Taken");
                photoGallery = populateGallery(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE));
                currentPhotoIndex = 0;
                currentPhotoPath = photoGallery.get(currentPhotoIndex);
                displayPhoto(currentPhotoPath);
            }
        }
    }

    public void onClick( View v) {
        switch (v.getId()) {
            case R.id.go_to_left_button:
                --currentPhotoIndex;
                break;
            case R.id.go_to_right_button:
                ++currentPhotoIndex;
                break;
            default:
                break;
        }
        if (currentPhotoIndex < 0)
            currentPhotoIndex = 0;
        if (currentPhotoIndex >= photoGallery.size())
            currentPhotoIndex = photoGallery.size() - 1;

        currentPhotoPath = photoGallery.get(currentPhotoIndex);
        Log.d("phpotoleft, size", Integer.toString(photoGallery.size()));
        Log.d("photoleft, index", Integer.toString(currentPhotoIndex));
        displayPhoto(currentPhotoPath);
    }

    /*
     * This function is for the Search Button in MainActivity.
     * This will open the Filter page after the button is clicked.
     */
    public void onClickToFilterActivity(View v){
        startActivityForResult(new Intent(MainActivity.this, FilterActivity.class), REQUEST_SEARCH_CODE);
    }

    public void takePicture(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                getLocation();
                photoFile = Photo.createImageFile(latitude, longitude, getExternalFilesDir(Environment.DIRECTORY_PICTURES));
                currentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Log.d("FileCreation", "Failed");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.michael.photogallery",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /*
     * Tells the ImageView to display a certain photo.
     * String path - variable indicating the path to a picture to be displayed.
     */
    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.gallery_picture);
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    /*
     * This method populates the gallery on main page.
     * The Date arguments provided are not yet implemented.
     * In its final version, this method will populate gallery with pictures from minDate to maxDate
     */
    private ArrayList<String> populateGallery(Date minDate, Date maxDate) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.michael.photogallery/files/Pictures");
        photoGallery = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : file.listFiles()) {
                Log.d("File Name: ",f.getName());
                Date date = Photo.photoGetDate(f.getName());
                if (date.compareTo(minDate) > 0 && date.compareTo(maxDate) < 0
                        && findOutIfInsideLocation(f.getName()) == true) {
                    photoGallery.add(f.getPath());
                }
            }
        }
        return photoGallery;
    }

    private void fillDate(Intent data) {
        if (data.getStringExtra("STARTDATE").toString().length() == 0)
            startDate = new Date(Long.MIN_VALUE);
        if (data.getStringExtra("ENDDATE").toString().length() == 0)
            endDate = new Date(Long.MAX_VALUE);

        try {
            startDate = new SimpleDateFormat("yyyyMMdd").parse(data.getStringExtra("STARTDATE"));
            endDate = new SimpleDateFormat("yyyyMMdd").parse(data.getStringExtra("ENDDATE"));
        } catch (ParseException e) {
            Log.d("Parse Exception", e.toString());
        }
    }

    private void resetDate() {
        startDate = new Date(Long.MIN_VALUE);
        endDate = new Date(Long.MAX_VALUE);
    }

    private void resetLocationFilter() {
        loc1 = new double[] {90, -180};
        loc2 = new double[] {-90, 180};
    }

    private boolean findOutIfInsideLocation(String location) {
        double[] loc = new double[2];
        try {
            loc = Photo.photoGetLocation(location);
        } catch (ParseException e) {
            Log.d("Parse Exception", e.toString());
        }
        if (loc[0] <= loc1[0] && loc[0] >= loc2[0]
                && loc[1] >= loc[1] && loc[1] <= loc2[1])
        {
            return true;
        }
        return false;
    }

}
