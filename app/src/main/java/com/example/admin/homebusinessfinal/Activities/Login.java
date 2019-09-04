package com.example.admin.homebusinessfinal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.homebusinessfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    //firebase Variables
    private FirebaseAuth firebaseAuth;

    //other variables
    EditText e_email;
    EditText e_pass;
    String email;
    String pass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up Toolbar and add up button
        Toolbar toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize firebase variables
        firebaseAuth = FirebaseAuth.getInstance();

        //initialize other variables
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Sign in");
        progressDialog.setIcon(R.drawable.icon);
        progressDialog.setMessage("Please wait...");
        e_email = findViewById(R.id.email);
        e_pass = findViewById(R.id.password);

        Button signIn = findViewById(R.id.SignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = e_email.getText().toString().trim();
                pass = e_pass.getText().toString().trim();
                if(validateUI(email,pass)){
                    progressDialog.show();
                    signIn(email,pass);
                }
            }
        });
    }

    //validating Entries in UI
    private boolean validateUI(String email, String pass) {
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            e_email.setError("Please Enter a valid Email Address");
            return false;
        }

        if(TextUtils.isEmpty(pass)){
            e_pass.setError("Please Enter Password");
            return false;
        }
        return true;
    }

    //go to create account activity
    public void openCreateAccount(View view){
        Intent intent = new Intent(Login.this,CreateAccount.class);
        startActivity(intent);
    }

    //for password reset link
    public void forgotPassword(View view){
        email = e_email.getText().toString().trim();
        if(!TextUtils.isEmpty(email)){
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Password Reset Link has bees sent to above mentioned Email Address",Toast.LENGTH_SHORT).show();
                    }else{
                        try{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }catch(NullPointerException ne){
                            ne.printStackTrace();
                            Toast.makeText(getApplicationContext(),"System error occured",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
        }else{
            Toast.makeText(this,"Please enter a valid email ID to send the password reset link",Toast.LENGTH_LONG).show();
        }
    }

   //sign in
    public void signIn(String email,String pass){

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,Profile.class);
                    startActivity(intent);
                }else {
                    progressDialog.dismiss();
                    try{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
    }
}
