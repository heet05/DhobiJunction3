package com.example.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.R;

public class HomeActivity extends AppCompatActivity {
ImageButton c,s,p,o;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        c=findViewById(R.id.category);
        s=findViewById(R.id.Subcategory);
        p=findViewById(R.id.product);
        o=findViewById(R.id.Orders);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,CatagroyActivity.class);
                startActivity(intent);
            }
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,Sub_CagtegoryActivity.class);
                startActivity(intent);
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}