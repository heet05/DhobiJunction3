package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dhobijunction2.priceEstimator.CategorySpinnerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Dashbord_ProductActivity extends AppCompatActivity {
    Spinner spinner1;
    EditText editText, e2, e3;
    Button button, bt1;
    private Uri filePath;
    ImageView imageView;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    List<String> savedImagesUri;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SubCatgeory subCatgeory;
    dashbordproductadpter dashbordproductadpter;
    RecyclerView recyclerView;
    List<SubCatgeory> titleList;
    CollectionReference reference;
    List<CustomModel> imagesList;
    int counter;
    StorageTask mUploadTask;
    private static final String KEY_TITLE = "Title";
    private static final String KEY_PRICE = "Price";
    private static final String KEY_PICE = "KgGm";
    private static final String KEY_ID = "Pid";
    private String Sid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord__product);
        spinner1 = findViewById(R.id.Product_spiner1);
        button = findViewById(R.id.dashboard_pro_b1);
        editText = findViewById(R.id.dashboard_pro_e1);
        e3 = findViewById(R.id.dashboard_pro_e3);
        e2 = findViewById(R.id.dashboard_pro_e2);
        bt1 = findViewById(R.id.product_select);
        imageView = findViewById(R.id.product_iv);
        // reference = firestore.collection("images");
        firebaseStorage = FirebaseStorage.getInstance();
        savedImagesUri = new ArrayList<>();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference("product");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uplodImage();
            }
        });

        recyclerView = findViewById(R.id.Product_spiner2);
        Query query = db.collection("subcategory");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot queryDocumentSnapshots = task.getResult();
                            titleList = new ArrayList<>();
                            for (DocumentSnapshot readData : queryDocumentSnapshots.getDocuments()) {
                                String titlename = readData.get("TITLE").toString();
                                String id = readData.get("Sid").toString();
                                subCatgeory = new SubCatgeory();
                                subCatgeory.setTITLE(titlename);
                                subCatgeory.setID(id);
                                titleList.add(subCatgeory);
                            }

                            SubCategorySpinnerAdapter categorySpinnerAdapter = new SubCategorySpinnerAdapter(Dashbord_ProductActivity.this, titleList);
                            spinner1.setAdapter(categorySpinnerAdapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dashbord_ProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Androidview", e.getMessage());
            }

        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sid = titleList.get(position).getID();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Query query1 = db.collection("product");
        FirestoreRecyclerOptions rvOptions = new FirestoreRecyclerOptions.Builder<SubCatgeory>()

                .setQuery(query1, SubCatgeory.class).build();
        dashbordproductadpter = new dashbordproductadpter(this, rvOptions);
        recyclerView.setAdapter(dashbordproductadpter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                StoreImage();


                if (editText.getText().toString().isEmpty()) {
                    editText.setError(editText.getText().toString().trim());
                } else {
                    String Pice = e3.getText().toString().trim();
                    String title = editText.getText().toString().trim();
                    String Price = e2.getText().toString().trim();
                    Map<String, String> note = new HashMap<>();
                    note.put(KEY_TITLE, title);
                    note.put(KEY_PRICE, Price);
                    note.put(KEY_PICE, Pice);
                    note.put("Sid", Sid);
                    String uploadeId = storageReference.getDownloadUrl().toString();
                    note.put("Image", uploadeId);


                    db.collection("product")
                            .add(note)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    note.put(KEY_ID, documentReference.getId().toString());

                                    Toast.makeText(Dashbord_ProductActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                                    db.collection("product").whereEqualTo(KEY_TITLE, note.get(KEY_TITLE))
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        Toast.makeText(Dashbord_ProductActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(Dashbord_ProductActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", e.toString());
                        }


                    });


                }
            }
        });

    }

    private void uplodImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Selcet Image"), 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    private void saveImageDataToFirestore(String progressDialog) {

        Map<String, String> dataMap = new HashMap<>();
        for (int i = 0; i < savedImagesUri.size(); i++) {
            dataMap.put("images" + i, savedImagesUri.get(i));
        }
        db.collection("product").add(dataMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(Dashbord_ProductActivity.this, "Images uploaded and saved successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Dashbord_ProductActivity.this, "Images uploaded but we couldn't save them to database", Toast.LENGTH_SHORT).show();
                //  coreHelper.createAlert("Error", "Images uploaded but we couldn't save them to database.", "OK", "", null, null, null);
                Log.e("MainActivity:SaveData", e.getMessage());
            }
        });
    }


    private void StoreImage() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            mUploadTask = ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }

    }

        }


