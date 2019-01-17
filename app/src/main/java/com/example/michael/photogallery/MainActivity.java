package com.example.michael.photogallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * This function is for the Search Button in MainActivity.
     * This will open the Filter page after the button is clicked.
     */
    public void onClickToFilterActivity(View v){
        startActivity(new Intent(MainActivity.this, FilterActivity.class));
    }
}
