package com.example.admin.homebusinessfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //firebase Variables
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up Toolbar and add up button
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        Button signIn = findViewById(R.id.SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void openCreateAccount(View view){
        Intent intent = new Intent(Login.this,CreateAccount.class);
        startActivity(intent);
    }

    public void signIn(){
        EditText e_email = findViewById(R.id.email);
        String email = e_email.getText().toString().trim();
        EditText e_pass = findViewById(R.id.password);
        String pass = e_pass.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Profile.class);
                    startActivity(intent);
                }else {
                    try{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }catch(NullPointerException ne){
                        ne.printStackTrace();
                        Toast.makeText(getApplicationContext(),"System Error occured while signin in...", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Login.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
