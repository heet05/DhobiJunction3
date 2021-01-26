package com.example.dhobijunction.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobijunction2.R;


public class OffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}