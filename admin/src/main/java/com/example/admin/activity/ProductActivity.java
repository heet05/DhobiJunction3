package com.example.admin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.R;

public class ProductActivity extends AppCompatActivity {
Button b1,b2;
ImageView imageView;
EditText e1,e2,e3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);
        e1.findViewById(R.id.e1);
        e2.findViewById(R.id.e2);
        e3.findViewById(R.id.e3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();

            }
        });
    }
}