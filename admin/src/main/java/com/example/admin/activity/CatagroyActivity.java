package com.example.admin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.CategoryAdapter;
import com.example.admin.Model.categoryModel;
import com.example.admin.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatagroyActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1;
    categoryModel model;
    RecyclerView rv;
    List<categoryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagroy);
        e1 = findViewById(R.id.e1);
        rv = findViewById(R.id.rv);
        b1 = findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model = new categoryModel();

                model.setTITLE(e1.getText().toString());
                FirebaseFirestore.getInstance().collection("category").add(model)
                        .addOnSuccessListener(documentReference -> {
                            String docId = documentReference.getId();
                            Map<String, Object> map = new HashMap<>();
                            map.put("cid", docId);
                            documentReference.update(map).addOnSuccessListener(aVoid -> {
                                Toast.makeText(CatagroyActivity.this, "success", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(CatagroyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                        }).addOnFailureListener(e -> {
                    Toast.makeText(CatagroyActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        FirebaseFirestore.getInstance().collection("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    list = value.toObjects(categoryModel.class);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CatagroyActivity.this);
                    rv.setLayoutManager(linearLayoutManager);
                    CategoryAdapter adapter = new CategoryAdapter(CatagroyActivity.this, list);
                    rv.setAdapter(adapter);
                }
            }
        });


    }

}