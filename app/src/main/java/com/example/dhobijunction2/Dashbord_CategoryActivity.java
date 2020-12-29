package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class Dashbord_CategoryActivity extends AppCompatActivity {
    Spinner spinner;
    RecyclerView listView ;
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_ID = "ID";
    CatgorylistAdapter catgorylistAdapter;
    EditText e1;
    Button b1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord__category);
        getSupportActionBar().setTitle("Category");
        listView = findViewById(R.id.catgory_spiner);
        b1 = findViewById(R.id.dashboard_b1);
        e1 = findViewById(R.id.dashboard_e1);

        Query query = db.collection("category");

        FirestoreRecyclerOptions rvOptions = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class).build();
         catgorylistAdapter = new CatgorylistAdapter(this, rvOptions);
        listView.setAdapter(catgorylistAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);



        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty()) {
                    e1.setError(e1.getText().toString().trim());
                } else {

                    String title = e1.getText().toString().trim();
                    Map<String,String> note=new HashMap<>();
                    note.put(KEY_TITLE,title);
                    db.collection("category").add(note)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    note.put(KEY_ID,documentReference.getId().toString());
                                    Toast.makeText(Dashbord_CategoryActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                                    db.collection("category").whereEqualTo(KEY_TITLE, note.get(KEY_TITLE))
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        Toast.makeText(Dashbord_CategoryActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Dashbord_CategoryActivity.this, "Error!", Toast.LENGTH_SHORT).show();
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
        catgorylistAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        catgorylistAdapter.stopListening();
    }
}