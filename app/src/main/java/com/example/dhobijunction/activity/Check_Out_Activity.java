package com.example.dhobijunction.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dhobijunction.R;
import com.example.dhobijunction.adapter.Check_out_Adapter;
import com.example.dhobijunction.databinding.ActivityCheckOutBinding;
import com.example.dhobijunction.model.CheckModel;
import com.example.dhobijunction.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Check_Out_Activity extends AppCompatActivity {
Check_out_Adapter adapter;
    String[] time = new String[]{"9:00AM-10:00AM", "10:00AM-11:00AM", "11:00AM-12:00PM", "12:00PM-1:00PM", "1:00PM-2:00PM", "2:00PM-3:00PM", "3:00PM-4:00PM", "4:00PM-5:00PM", "5:00PM-6:00PM"};
    ActivityCheckOutBinding screen;
    SharedPreferences pref;
    String mobile = "";
    String total="";
    List<CheckModel> list;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(screen.getRoot());

        total=getIntent().getStringExtra("total");
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pref = getSharedPreferences("Users", 0);
        mobile = pref.getString("userMobile", "");
        List<String> timelist = new ArrayList<>(Arrays.asList(time));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, timelist);

        screen.payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderModel model=new OrderModel();
                model.setName(screen.checkoutName.getText().toString());
                model.setEmail(screen.checkoutEmail.getText().toString());
                model.setNumber(screen.checkoutMobilenumber.getText().toString());
                model.setAddress(screen.checkoutAddress.getText().toString());

                FirebaseFirestore.getInstance().collection("USERS")
                        .document(mobile).collection("USERCART").addSnapshotListener((value, error) -> {
                            if (value!=null&&!value.isEmpty()){
                                list=value.toObjects(CheckModel.class);
                                model.setModelList(list);

                                Intent intent=new Intent(Check_Out_Activity.this,PaymentActivity.class);
                                intent.putExtra("total",total);
                                intent.putExtra("order",model);
//                                intent.putExtra("name",  screen.checkoutName.getText());
//                                intent.putExtra("email", screen.checkoutEmail.getText());
//                                intent.putExtra("number", screen.checkoutMobilenumber.getText());
//                                intent.putExtra("Address",screen.checkoutAddress);
//
                                startActivity(intent);
                            }
                            if (error!=null){
                                Toast.makeText(Check_Out_Activity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                });



//                FirebaseFirestore.getInstance().collection("USERS").document(pref.getString("userMobile", "")).collection("Order").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("cartItemId", documentReference.getId());
//                        FirebaseFirestore.getInstance().collection("USERS")
//                                .document(pref.getString("userMobile", ""))
//                                .collection("Order").document(documentReference.getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(Check_Out_Activity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });


            }
        });

        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART");
        FirestoreRecyclerOptions<CheckModel> rvOptions = new FirestoreRecyclerOptions.Builder<CheckModel>()
                .setQuery(query, CheckModel.class).build();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        screen.checkRecyclerview.setLayoutManager(linearLayoutManager);
       adapter = new Check_out_Adapter(this, rvOptions, this);
      screen.checkRecyclerview.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}