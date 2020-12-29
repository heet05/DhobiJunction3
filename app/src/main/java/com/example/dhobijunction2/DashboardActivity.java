package com.example.dhobijunction2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {
ImageButton i1,i2,i3,i4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("DashBoard");
        i1=findViewById(R.id.dashbord_ivcategory);
        i2=findViewById(R.id.dashbord_ivSubcategory);
        i3=findViewById(R.id.dashbord_ivProduct);
        i4=findViewById(R.id.dashbord_ivOrder);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this,Dashbord_CategoryActivity.class);
                startActivity(intent);


            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this,Dashbord_SubcatgoryActivity.class);
                startActivity(intent);
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this,Dashbord_ProductActivity.class);
                startActivity(intent);
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this,Dashbord_orderActivity.class);
                startActivity(intent);

            }
        });

    }
}