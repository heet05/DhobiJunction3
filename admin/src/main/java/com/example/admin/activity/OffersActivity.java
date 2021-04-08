package com.example.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.databinding.ActivityOffersBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OffersActivity extends AppCompatActivity {
ActivityOffersBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOffersBinding.inflate(getLayoutInflater());
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
                Map map=new HashMap<>();
                map.put("title",binding.promeTitle.getText().toString());
                map.put("price",binding.promePrice.getText().toString());
                map.put("code",binding.promeCode.getText().toString());
                map.put("date",binding.promeExdate.getText().toString());
                FirebaseFirestore.getInstance().collection("offer").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(OffersActivity.this, "add offer", Toast.LENGTH_SHORT).show();
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