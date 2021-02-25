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
        pref = getSharedPreferences("Users", 0);

        mobile = pref.getString("userMobile", "");
        textView.setText(Model.getTotal());

        adapter = new orderDetaliAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}