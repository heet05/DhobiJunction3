package com.example.admin.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.orderDetaliAdapter;
import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OrderdetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences pref;
    orderDetaliAdapter adapter;
    String total = "";
    orderModel Model;
    String mobile = "";

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.Order_detail_rv);
        textView = findViewById(R.id.Order_detail_total);

        Model = (orderModel) getIntent().getSerializableExtra("Model");

        textView.setText(Model.getTotal());
        Query query = FirebaseFirestore.getInstance().collectionGroup("ORDERS");
        FirestoreRecyclerOptions<orderModel> rvOptions = new FirestoreRecyclerOptions.Builder<orderModel>()
                .setQuery(query, orderModel.class).build();
        adapter = new orderDetaliAdapter(this,rvOptions,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}