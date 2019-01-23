package com.example.michael.photogallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
    }

    public void goBack(View v) {
        this.finish();
    }

    public void search(View v) {
        searchAlgorithm();
        // get results from the searchAlgorithm method and send it with new Intent
        startActivity(new Intent(FilterActivity.this, MainActivity.class));
    }

    private void searchAlgorithm() {
        // Search algorithm in here
    }
}
