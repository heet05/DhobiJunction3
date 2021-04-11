package com.example.admin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.admin.Model.OfferModel;
import com.example.admin.R;
import com.example.admin.databinding.ActivityOffersBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OffersActivity extends AppCompatActivity {
ActivityOffersBinding binding;
OfferModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOffersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.promeExdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popclander();
            }

        });
        binding.btnPromcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* FirebaseFirestore.getInstance().collection("offer").addSnapshotListener(new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                     if (value != null && !value.isEmpty()) {
                         for (int i=0;i<value.size();i++) {
                             String id = value.getDocuments().get(0).getId();
                                                     map1.put("offerId", id);
                             FirebaseFirestore.getInstance().collection("offer").add(map1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                 @Override
                                 public void onSuccess(DocumentReference documentReference) {
                                     Toast.makeText(OffersActivity.this, "Add OFFER ", Toast.LENGTH_SHORT).show();
                                 }
                             });
                         }
                     }

                 }
             });*/
                model = new OfferModel();
                model.setCode("code", binding.promeCode.getText().toString());
                model.setDate("date", binding.promeExdate.getText().toString());
                model.setPrice("price", binding.promePrice.getText().toString());
                model.setTitle("title", binding.promeTitle.getText().toString());

FirebaseFirestore.getInstance().collection("offer").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
    @Override
    public void onSuccess(DocumentReference documentReference) {
Map map=new HashMap();
map.put("offerid",documentReference.getId());
    documentReference.update(map).addOnSuccessListener(new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            Toast.makeText(OffersActivity.this, "Add Offer", Toast.LENGTH_SHORT).show();
        }
    });
    }
});



            }
        });
    }

    private void popclander() {
        Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int mMonth=c.get(Calendar.MONTH);
        int mDay=c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(OffersActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                DecimalFormat format=new DecimalFormat("00");
                String pDay=format.format(dayOfMonth);
                String pMonth=format.format(monthOfYear);
                String pYear=""+(year);
                String pDate=pDay+"/"+pMonth+"/"+pYear;
                binding.promeExdate.setText(pDate);


            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }
}