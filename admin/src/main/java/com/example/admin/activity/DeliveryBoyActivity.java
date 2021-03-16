package com.example.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.Adapter.DeliveryboyAdapter;
import com.example.admin.Adapter.OrderAdapter;
import com.example.admin.Model.categoryModel;
import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class DeliveryBoyActivity extends AppCompatActivity {
    EditText e1, e2,e3,e4;
    Button b1;
    DeliveryModel model;
    RecyclerView rv;
    DeliveryboyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);
        e1=findViewById(R.id.d_e1);
        e2=findViewById(R.id.d_e2);
        e3=findViewById(R.id.d_e3);
        e4=findViewById(R.id.d_e4);
        rv=findViewById(R.id.d_rv);
        b1=findViewById(R.id.d_b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model = new DeliveryModel();

                model.setName(e1.getText().toString());
                model.setPhone(e2.getText().toString());
                model.setEmail(e3.getText().toString());
                model.setPassword(e4.getText().toString());
                FirebaseFirestore.getInstance().collection("deliveryboy").add(model)
                        .addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("did", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(DeliveryBoyActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(DeliveryBoyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                    Toast.makeText(DeliveryBoyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
        Query query = FirebaseFirestore.getInstance().collectionGroup("deliveryboy");
        FirestoreRecyclerOptions<DeliveryModel> rvOptions = new FirestoreRecyclerOptions.Builder<DeliveryModel>()
                .setQuery(query, DeliveryModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        adapter = new DeliveryboyAdapter(this, rvOptions, this);
        rv.setAdapter(adapter);
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