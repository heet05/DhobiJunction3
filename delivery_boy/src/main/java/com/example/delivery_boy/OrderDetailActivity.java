package com.example.delivery_boy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SharedPreferences pref;
    OrderDetailAdapter adapter;
    String total = "";
    OrderModel Model;
    String mobile = "";
    TextView textView;
    Button cancelorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        recyclerView = findViewById(R.id.Order_detail_rv);
        cancelorder = findViewById(R.id.order_detail_button1);
        textView = findViewById(R.id.Order_detail_total);
        Model = (OrderModel) getIntent().getSerializableExtra("Model");
        pref=getSharedPreferences("deliveryBoy",MODE_PRIVATE);
        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("deliveryboy").whereEqualTo("did",pref.getString("did","")).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value!=null && !value.isEmpty()){
                        value.getDocuments().get(0).getReference().collection("Order").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                value.getDocuments().get(0).getReference().delete();
                                Toast.makeText(OrderDetailActivity.this, "Order Has been Cancel", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    }
                });
            }
        });
        textView.setText(Model.getTotal());
        adapter = new OrderDetailAdapter(this, Model.getModelList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}