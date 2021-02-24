package com.example.admin.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.CategoryAdapter;
import com.example.admin.Model.categoryModel;
import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatagroyActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1;
    categoryModel model;
    RecyclerView rv;
    List<categoryModel>list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagroy);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        rv = findViewById(R.id.rv);
        b1 = findViewById(R.id.b1);
        model = new categoryModel();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.setCid(e2.getText().toString());
                model.setTITLE(e1.getText().toString());
                FirebaseFirestore.getInstance().collection("category").add(model).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CatagroyActivity.this, "Add Category SuccessFull", Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            }
        });

        FirebaseFirestore.getInstance().collection("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && !value.isEmpty()){
                    list=value.toObjects(categoryModel.class);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(CatagroyActivity.this);
                    rv.setLayoutManager(linearLayoutManager);
                    CategoryAdapter adapter=new CategoryAdapter(CatagroyActivity.this,list);
                    rv.setAdapter(adapter);
                }
            }
        });

    }
}