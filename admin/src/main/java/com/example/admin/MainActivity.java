package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tuyenmonkey.mkloader.MKLoader;

public class MainActivity extends AppCompatActivity {
    EditText Memail,Mpassword;
    Button login;
    FirebaseAuth mAuth;

    private MKLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Memail=findViewById(R.id.login_email);
        loader=findViewById(R.id.loader);
        Mpassword=findViewById(R.id.login_password);
        login=findViewById(R.id.login_btn);
        mAuth=FirebaseAuth.getInstance();
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

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (Memail.getText().toString().equals("admin@gmail.com")){
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                loader.setVisibility(View.GONE);
                            }
                        }else {
                            Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            loader.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });
    }
}
