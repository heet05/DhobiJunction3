package com.example.admin.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.OrderAdapter;
import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OrderActivity extends AppCompatActivity {
    SharedPreferences pref;
    OrderAdapter adapter;
    String total="";
    String mobile = "";
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView=findViewById(R.id.rv);
        pref = getSharedPreferences("Users", 0);
        mobile = pref.getString("userMobile", "");


        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("ORDERS");
        FirestoreRecyclerOptions<orderModel> rvOptions = new FirestoreRecyclerOptions.Builder<orderModel>()
                .setQuery(query, orderModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderAdapter(this, rvOptions, this);
        recyclerView.setAdapter(adapter);

    }
}