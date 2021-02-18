package com.example.dhobijunction.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OrderDetaliAdapter;
import com.example.dhobijunction.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OrderDetaliActivity extends AppCompatActivity {
RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetaliAdapter adapter;
    String total="";
    OrderModel Model;
    String mobile = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detali);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.Order_detail_rv);
        pref = getSharedPreferences("Users",0);
        mobile = pref.getString("userMobile", "");
        Model= (OrderModel) getIntent().getSerializableExtra("Model");

        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("ORDERS");
        FirestoreRecyclerOptions<OrderModel> rvOptions = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query, OrderModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderDetaliAdapter(this, rvOptions, this);
        recyclerView.setAdapter(adapter);

    }
}