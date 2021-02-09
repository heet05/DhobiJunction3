package com.example.dhobijunction.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.Check_out_Adapter;
import com.example.dhobijunction.databinding.ActivityCheckOutBinding;
import com.example.dhobijunction.model.CheckModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Check_Out_Activity extends AppCompatActivity {
Check_out_Adapter adapter;
    String[] time = new String[]{"9:00AM-10:00AM", "10:00AM-11:00AM", "11:00AM-12:00PM", "12:00PM-1:00PM", "1:00PM-2:00PM", "2:00PM-3:00PM", "3:00PM-4:00PM", "4:00PM-5:00PM", "5:00PM-6:00PM"};
    ActivityCheckOutBinding screen;
    SharedPreferences pref;
    String mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(screen.getRoot());


        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("Users", 0);
        mobile = pref.getString("userMobile", "");
        List<String> timelist = new ArrayList<>(Arrays.asList(time));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, timelist);
        screen.checkoutSpinner.setAdapter(spinnerArrayAdapter);
        screen.checkoutEmail.setError("Enter Email");
        screen.checkoutMobilenumber.setError("Enter Your PhoneNumber");
        screen.checkoutAddress.setError("Enter Your Address");
        screen.checkoutName.setError("Enter Your Name");
        screen.checkoutTv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Check_Out_Activity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART");
        FirestoreRecyclerOptions<CheckModel> rvOptions = new FirestoreRecyclerOptions.Builder<CheckModel>()
                .setQuery(query, CheckModel.class).build();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        screen.checkRecyclerview.setLayoutManager(linearLayoutManager);
       adapter = new Check_out_Adapter(this, rvOptions, this);
      screen.checkRecyclerview.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}