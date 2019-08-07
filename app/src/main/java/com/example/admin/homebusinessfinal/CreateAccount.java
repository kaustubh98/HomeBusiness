package com.example.admin.homebusinessfinal;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    //Firebase Variables
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    //other variables
    EditText e_email,e_pass,e_name,e_contact,e_address,e_des;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //set up toolbar and up button
        Toolbar toolbar = findViewById(R.id.toolbar_createAccount);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        //initialize other variables
        e_email = findViewById(R.id.email_create);
        e_pass = findViewById(R.id.password_create);
        e_name = findViewById(R.id.name);
        e_address = findViewById(R.id.address);
        e_contact = findViewById(R.id.contactNo);
        e_des = findViewById(R.id.description);

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
        String email = e_email.getText().toString().trim();
        String pass = e_pass.getText().toString().trim();
        String name = e_name.getText().toString().trim();
        String address = e_address.getText().toString().trim();
        String contact = e_contact.getText().toString().trim();
        String des = e_des.getText().toString().trim();

        progressDialog.setMessage("Registering: "+name);

        //create account only if entries in UI are validated
        if(validateUI()){
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //registration of user is successfull. Now we can add data
                        progressDialog.setMessage("Storing Your Information...");
                        storeData();

                    }else {
                        progressDialog.dismiss();
                        try{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }catch (NullPointerException ne){
                            ne.printStackTrace();
                            Toast.makeText(getApplicationContext(),"System Error Occured",Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });

        }

    }

    //store the data entered by the user
    private void storeData() {
        try{
            String email = e_email.getText().toString().trim();
            String name = e_name.getText().toString().trim();
            String address = e_address.getText().toString().trim();
            String contact = e_contact.getText().toString().trim();
            String des = e_des.getText().toString().trim();

            if(TextUtils.isEmpty(des)){
                des = "No description provided";
            }

            user = FirebaseAuth.getInstance().getCurrentUser();
            if(user!=null) {
                DatabaseReference db = databaseReference.child(user.getUid());
                db.child("Name").setValue(name);
                db.child("Contact").setValue(contact);
                db.child("Description").setValue(des);
                db.child("Email").setValue(email);
                db.child("Address").setValue(address);
            }

            progressDialog.dismiss();
            Toast.makeText(this,"Successfully created Account",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateAccount.this,Profile.class);
            startActivity(intent);

        }catch(Exception e){
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //validate the entries in UI
    private boolean validateUI(){
        String email = e_email.getText().toString().trim();
        String pass = e_pass.getText().toString().trim();
        String name = e_name.getText().toString().trim();
        String address = e_address.getText().toString().trim();
        String contact = e_contact.getText().toString().trim();

        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            e_email.setError("Please Enter a valid Email Address");
            return false;
        }

        if(TextUtils.isEmpty(pass) || pass.length()<6){
            e_pass.setError("Please Enter Password of more than 6 characters");
            return false;
        }

        if(TextUtils.isEmpty(name)){
            e_name.setError("Please Enter name");
            return false;
        }

        if(TextUtils.isEmpty(address)){
            e_address.setError("Please Enter address");
            return false;
        }

        if(TextUtils.isEmpty(contact) || !TextUtils.isDigitsOnly(contact) || contact.length()!=10){
            e_contact.setError("Please Enter a valid contact number");
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CreateAccount.this,MainActivity.class);
        startActivity(intent);
    }
}
