package com.example.delivery_boy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationAttributes;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    SharedPreferences pref;
    OrderAdapter adapter;
    OrderModel Model;
    DeliveryModel deliveryModel;
    String total = "";
    String mobile = "";
    SharedPreferences preferences;
    RecyclerView recyclerView;
    List<DeliveryModel>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setTitle("Order");


        preferences=getSharedPreferences("deliveryBoy",MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.o_rv);
        FirebaseFirestore.getInstance().collection("deliveryboy").whereEqualTo("did",preferences.getString("did","")).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value!=null && !value.isEmpty()){

                    Query query=value.getDocuments().get(0).getReference().collection("Order");
                    FirestoreRecyclerOptions<OrderModel> rvOptions = new FirestoreRecyclerOptions.Builder<OrderModel>()
                            .setQuery(query, OrderModel.class).build();



                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new OrderAdapter(OrderActivity.this, rvOptions, OrderActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.stopListening();
                    adapter.startListening();
                }
                }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}