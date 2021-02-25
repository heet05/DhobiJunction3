package com.example.admin.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.SubCategoryADapter;
import com.example.admin.Model.SubCategoryModel;
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

public class Sub_CagtegoryActivity extends AppCompatActivity {
Spinner spinner;
EditText editText;
RecyclerView recyclerView;
    SubCategoryModel model;
Button button;
SubCategoryADapter  aDapter;
List<categoryModel>list;
List<String> categoryList=new ArrayList<>();
List<SubCategoryModel>modelList;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub__cagtegory2);
        getSupportActionBar().hide();
        list=new ArrayList<>();
        spinner=findViewById(R.id.s1);
        editText=findViewById(R.id.edit_sub);
       recyclerView=findViewById(R.id.rv);
        button=findViewById(R.id.b1);

        FirebaseFirestore.getInstance().collection("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
           if (value !=null && !value.isEmpty()) {
               list =value.toObjects(categoryModel.class);
               for (categoryModel catergoty:list){
                   categoryList.add(catergoty.getTITLE());
               }
               ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(Sub_CagtegoryActivity.this,R.layout.support_simple_spinner_dropdown_item,categoryList);
                spinner.setAdapter(arrayAdapter);

           }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model =new SubCategoryModel();

                for (int i=0;i<list.size();i++){
                  if (list.get(i).getTITLE()==spinner.getSelectedItem().toString()){
                      model.setCid(list.get(i).getCid());
                      model.setTITLE(editText.getText().toString());


                      FirebaseFirestore.getInstance().collection("subcategory" ).add(model).addOnSuccessListener(documentReference -> {
                          String docId = documentReference.getId();
                          Map<String, Object> map = new HashMap<>();
                          map.put("sid", docId);
                          documentReference.update(map).addOnSuccessListener(aVoid -> {
                              Toast.makeText(Sub_CagtegoryActivity.this, "success", Toast.LENGTH_SHORT).show();
                          }).addOnFailureListener(e -> {
                              Toast.makeText(Sub_CagtegoryActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                          });
                      }).addOnFailureListener(e -> {
                          Toast.makeText(Sub_CagtegoryActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                      });
                  }
               }


            }
        });
        FirebaseFirestore.getInstance().collection("subcategory").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                               modelList=value.toObjects(SubCategoryModel.class);
                               aDapter=new SubCategoryADapter(Sub_CagtegoryActivity.this,modelList);
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Sub_CagtegoryActivity.this);
                    recyclerView.setAdapter(aDapter);
                    recyclerView.setLayoutManager(linearLayoutManager);

                }
            }
        });


    }
}