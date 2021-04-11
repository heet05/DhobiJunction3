package com.example.dhobijunction.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OfferAdapter;
import com.example.dhobijunction.databinding.ActivityOffersBinding;
import com.google.firebase.firestore.FirebaseFirestore;


public class OffersActivity extends AppCompatActivity {
ActivityOffersBinding activityOffersBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOffersBinding= ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(activityOffersBinding.getRoot());
        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        activityOffersBinding.offerRv.setLayoutManager(linearLayoutManager);
        OfferAdapter offerAdapter =new OfferAdapter(this);
        activityOffersBinding.offerRv.setAdapter(offerAdapter);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}