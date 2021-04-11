package com.example.dhobijunction.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dhobijunction.adapter.OfferAdapter;
import com.example.dhobijunction.databinding.ActivityOffersBinding;
import com.example.dhobijunction.model.OfferModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class OffersActivity extends AppCompatActivity {
ActivityOffersBinding activityOffersBinding;
    OfferAdapter offerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOffersBinding= ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(activityOffersBinding.getRoot());
        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Query query = FirebaseFirestore.getInstance().collection("offer");
        FirestoreRecyclerOptions<OfferModel> rvOptions = new FirestoreRecyclerOptions.Builder<OfferModel>()
                .setQuery(query, OfferModel.class).build();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        activityOffersBinding.offerRv.setLayoutManager(linearLayoutManager);
        offerAdapter =new OfferAdapter(this,rvOptions,this);
        activityOffersBinding.offerRv.setAdapter(offerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        offerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        offerAdapter.stopListening();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}