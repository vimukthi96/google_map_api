package com.example.vimukthi.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverActivity extends AppCompatActivity {
    private EditText editEmail, editPassward;
    private Button btnLogin, btnRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseAuthListner =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent =new Intent(DriverActivity.this,MapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };


        editEmail=(EditText)findViewById(R.id.email);
        editPassward=(EditText)findViewById(R.id.passward);
        btnLogin = (Button) findViewById(R.id.btnCustomer);
        btnRegister = (Button) findViewById(R.id.btnDriver);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String passward = editPassward.getText().toString();
                final String email = editEmail.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email,passward).addOnCompleteListener(DriverActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverActivity.this,"Sign Up Error",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id =firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id);
                            current_user_db.setValue(true);

                        }
                    }
                });
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= editEmail.getText().toString();
                final String passward =editPassward.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email,passward).addOnCompleteListener(DriverActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverActivity.this,"Sign In Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListner);
    }
}

