package com.example.admin.homebusinessfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {

    ProgressDialog progressDialog;

    //Firebase Variables
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //set up toolbar and up button
        Toolbar toolbar = findViewById(R.id.toolbar_createAccount);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //signout if a user is signed in
        if(user != null){
            firebaseAuth.signOut();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Create Account");
        progressDialog.setIcon(R.drawable.icon);

    }

    //create the account
    public void createAccount(View view){
        progressDialog.show();

        EditText e_email = findViewById(R.id.email_create);
        String email = e_email.getText().toString().trim();
        EditText e_pass = findViewById(R.id.password_create);
        String pass = e_pass.getText().toString().trim();
        EditText e_name = findViewById(R.id.name);
        String name = e_name.getText().toString().trim();

        progressDialog.setMessage("Registering: "+name);

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registered user",Toast.LENGTH_SHORT).show();

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateAccount.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
