package com.example.admin.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Adapter.productAdapter;
import com.example.admin.Model.ProductModel;
import com.example.admin.Model.SubCategoryModel;
import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductActivity extends AppCompatActivity {
Button b1,b2;
ImageView imageView;
EditText ed1,ed2,ed3;
Spinner spinner;
    productAdapter adapter;
    Uri filePath;
    ProductModel model;
    private final int PICK_IMAGE_REQUEST = 71;
    List<ProductModel> modelList;
    List<SubCategoryModel> list;
    FirebaseStorage storage;
    RecyclerView recyclerView;
    StorageReference storageReference;

    List<String> subcategoryList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        b1=findViewById(R.id.bt1);
        b2=findViewById(R.id.bt2);
        spinner=findViewById(R.id.pr_sp);
        ed1=findViewById(R.id.ed1);
        ed2=findViewById(R.id.ed2);
        ed3=findViewById(R.id.ed3);
        recyclerView=findViewById(R.id.rv);
        imageView=findViewById(R.id.imageView);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



                FirebaseFirestore.getInstance().collection("product").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()) {
                            modelList = value.toObjects(ProductModel.class);
                            adapter = new productAdapter(ProductActivity.this, modelList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductActivity.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(linearLayoutManager);

                        }
                    }
                });
        


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
        FirebaseFirestore.getInstance().collection("subcategory").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && !value.isEmpty()) {
                    list =value.toObjects(SubCategoryModel.class);
                    for (SubCategoryModel subCategoryModel:list){
                        subcategoryList.add(subCategoryModel.getTITLE());
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ProductActivity.this,R.layout.support_simple_spinner_dropdown_item,subcategoryList);
                    spinner.setAdapter(arrayAdapter);

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model=new ProductModel();

                for (int i=0;i<list.size();i++){
                    if (list.get(i).getTITLE()==spinner.getSelectedItem().toString()){
                        model.setSid(list.get(i).getSid());
                        model.setTitle(ed1.getText().toString());
                        model.setPrice(ed2.getText().toString());
                        model.setQty(ed3.getText().toString());
                        final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();


                        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                        ref.putFile(filePath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                model.setImage(  task.getResult().toString());

                                                FirebaseFirestore.getInstance().collection("product").add(model).addOnSuccessListener(documentReference -> {
                                                    String docId = documentReference.getId();
                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("pid", docId);
                                                    documentReference.update(map).addOnSuccessListener(aVoid -> {
                                                        if(filePath != null)
                                                        {
                                                            final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                                                            progressDialog.setTitle("Uploading...");
                                                            progressDialog.show();
                                                            progressDialog.dismiss();

                                                        }
                                                    });
                                                });
                                            }
                                        });

                                        // Continue with the task to get the download URL

                                        progressDialog.dismiss();

                                        Toast.makeText(ProductActivity.this, "success", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(ProductActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ProductActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                .getTotalByteCount());

                                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                    }
                                });


                    }
                }
            }
        });
    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
             filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}