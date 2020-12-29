package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.dhobijunction2.priceEstimator.CategorySpinnerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashbord_SubcatgoryActivity extends AppCompatActivity {
    Spinner spinner1;
    EditText editText;
    Button button;
    Category category;
    SubcatgorylistAdapter subcatgorylistAdapter;
    RecyclerView recyclerView;
    List<Category> titleList;
    String cid="";
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_ID = "Sid";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord__subcatgory);
        getSupportActionBar().setTitle("SubCategory");
        spinner1 = findViewById(R.id.Subcatgory_spiner1);
        button = findViewById(R.id.dashboard_sub_b1);
        editText = findViewById(R.id.dashboard_sub_e1);
        recyclerView = findViewById(R.id.Subcatgory_spiner2);
        Query query = db.collection("category");

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot queryDocumentSnapshots = task.getResult();
                            titleList = new ArrayList<>();
                            for (DocumentSnapshot readData : queryDocumentSnapshots.getDocuments()) {
                                String titlename = readData.get("TITLE").toString();
                                String id = readData.get("ID").toString();
                                category = new Category();
                                category.setTITLE(titlename);
                                category.setID(id);
                                titleList.add(category);
                            }

                            CategorySpinnerAdapter categorySpinnerAdapter = new CategorySpinnerAdapter(Dashbord_SubcatgoryActivity.this, titleList);
                            spinner1.setAdapter(categorySpinnerAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dashbord_SubcatgoryActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Androidview", e.getMessage());
            }

        });


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               cid=titleList.get(position).getID();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Query query1 = db.collection("subcategory");
        FirestoreRecyclerOptions rvOptions = new FirestoreRecyclerOptions.Builder<SubCatgeory>()

                .setQuery(query1, SubCatgeory.class).build();
        subcatgorylistAdapter = new SubcatgorylistAdapter(this, rvOptions);
        recyclerView.setAdapter(subcatgorylistAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError(editText.getText().toString().trim());
                } else {
                    String title = editText.getText().toString().trim();
                    Map<String, String> note = new HashMap<>();
                    note.put(KEY_TITLE, title);
                    note.put("Cid",cid );

                    db.collection("subcategory")
                            .add(note)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    note.put(KEY_ID, documentReference.getId().toString());

                                    Toast.makeText(Dashbord_SubcatgoryActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                                    db.collection("subcategory").whereEqualTo(KEY_TITLE, note.get(KEY_TITLE))
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        Toast.makeText(Dashbord_SubcatgoryActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                                    }
                                                    if (value != null && !value.isEmpty()) {
                                                        value.getDocuments().get(0).getReference().set(note);
                                                    }
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Dashbord_SubcatgoryActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }


                    });




                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        subcatgorylistAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        subcatgorylistAdapter.stopListening();
    }
}