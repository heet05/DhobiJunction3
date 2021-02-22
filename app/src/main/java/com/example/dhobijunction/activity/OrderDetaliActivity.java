package com.example.dhobijunction.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OrderDetaliAdapter;
import com.example.dhobijunction.model.OrderModel;

import java.util.List;

public class OrderDetaliActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetaliAdapter adapter;
    String total = "";
    OrderModel Model;
    String mobile = "";
    List<OrderModel> list;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detali);
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.Order_detail_rv);
        textView = findViewById(R.id.Order_detail_total);

        Model = (OrderModel) getIntent().getSerializableExtra("Model");
        pref = getSharedPreferences("Users", 0);

        mobile = pref.getString("userMobile", "");
        textView.setText(Model.getTotal());

        adapter = new OrderDetaliAdapter(this, Model.getModelList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}