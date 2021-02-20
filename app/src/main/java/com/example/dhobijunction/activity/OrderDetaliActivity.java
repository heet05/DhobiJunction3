package com.example.dhobijunction.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.OrderDetaliAdapter;
import com.example.dhobijunction.model.CheckModel;
import com.example.dhobijunction.model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetaliActivity extends AppCompatActivity {
RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetaliAdapter adapter;
    String total="";
    OrderModel Model;
    String mobile = "";
    List<CheckModel> list;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detali);
        getSupportActionBar().hide();
        recyclerView=findViewById(R.id.Order_detail_rv);
       textView=findViewById(R.id.Order_detail_total);
        pref = getSharedPreferences("Users",0);
        mobile = pref.getString("userMobile", "");
      //  Model= (OrderModel) getIntent().getSerializableExtra("Model");
//        textView.setText(Model.getTotal());

list=new ArrayList<>();

       adapter=new OrderDetaliAdapter(this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}