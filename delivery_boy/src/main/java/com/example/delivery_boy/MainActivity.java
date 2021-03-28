package com.example.delivery_boy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    MKLoader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.login_email);
        e2=findViewById(R.id.login_password);
        b1=findViewById(R.id.login_btn);
        loader=findViewById(R.id.loader);

        sharedPreferences=getSharedPreferences("deliveryBoy",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Enter Email Id", Toast.LENGTH_SHORT).show();
                }else if(e2.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                FirebaseFirestore.getInstance().collection("deliveryboy" ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc :task.getResult()){
                                String a=doc.getString("email");
                                String b=doc.getString("password");
                                String a1=e1.getText().toString().trim();
                                String b1=e2.getText().toString().trim();
                                if (a.equalsIgnoreCase(a1)& b.equalsIgnoreCase(b1)){
                                    Intent intent=new Intent(MainActivity.this,homeActivity.class);
                                    startActivity(intent);
                                    editor.putString("email",a);
                                    editor.putString("did",doc.getString("did"));
                                    editor.putString("phone",doc.getString("phone")) ;
                                    editor.apply();
                                    Toast.makeText(MainActivity.this, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                                else {
                                    Toast.makeText(MainActivity.this, "CanNot login ,Incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                }


                            }

                        }

                    }
                });

            }
        });
    }
}
