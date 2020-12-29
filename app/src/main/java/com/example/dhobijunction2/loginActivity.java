package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tuyenmonkey.mkloader.MKLoader;

public class loginActivity extends AppCompatActivity {
    EditText Memail,Mpassword;
    Button login,phone;
    private FirebaseAuth mAuth;
    private MKLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        Memail=findViewById(R.id.login_email);
        loader=findViewById(R.id.loader);
        Mpassword=findViewById(R.id.login_password);
        login=findViewById(R.id.login_btn);
        phone=findViewById(R.id.login_phone);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Memail.getText().toString().trim();
                String password = Mpassword.getText().toString().trim();
                loader.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    Memail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Mpassword.setError("Password is Required.");
                    return;
                }


                if(password.length() < 6){
                    Mpassword.setError("Password Must be >= 6 Characters");
                    return;
                }





                // authenticate the user

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (Memail.getText().toString().equals("admin@gmail.com")){
                                Toast.makeText(loginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                                loader.setVisibility(View.GONE);
                            }else{
                            Toast.makeText(loginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Navigation1Activity.class));
                            loader.setVisibility(View.GONE);}
                        }else {
                            Toast.makeText(loginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loader.setVisibility(View.GONE);
                        }

                    }
                });


            }
        });

            }

    private void updateUI(Object o) {
    }
}