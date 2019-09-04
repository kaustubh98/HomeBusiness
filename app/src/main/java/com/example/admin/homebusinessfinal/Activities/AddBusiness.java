package com.example.admin.homebusinessfinal.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.homebusinessfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBusiness extends AppCompatActivity {

    //firebase variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    //other variables
    Spinner category;
    EditText e_name,e_description,e_location;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        //set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_addBusiness);
        setSupportActionBar(toolbar);

        //initialize firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //initialize other variables
        category = findViewById(R.id.spinner_category);
        e_description = findViewById(R.id.busi_description);
        e_location = findViewById(R.id.busi_address);
        e_name = findViewById(R.id.busi_name);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Registering your business");
        progressDialog.setIcon(R.drawable.icon);
        progressDialog.setMessage("Please wait...");

        //if not signed in, Sign in first
        if(user == null){
            Intent intent = new Intent(AddBusiness.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    //register business
    public void registerBusiness(View view){
        String name = e_name.getText().toString().trim();
        String des = e_description.getText().toString().trim();
        String location = e_location.getText().toString().trim();
        String cat = category.getSelectedItem().toString().trim();

        try{
            if(validateUI()){
                progressDialog.show();
                user = firebaseAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference().child(cat).child(user.getUid());
                databaseReference.child("Name").setValue(name);
                databaseReference.child("Description").setValue(des);
                databaseReference.child("Location").setValue(location);
                databaseReference.child("Owner").setValue(user.getUid());
                progressDialog.dismiss();
                Toast.makeText(this,"Registered your Business",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddBusiness.this,Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Some Error Occured while registering your business",Toast.LENGTH_LONG).show();
        }
    }

    private boolean validateUI() {
        String name = e_name.getText().toString().trim();
        String des = e_description.getText().toString().trim();
        String location = e_location.getText().toString().trim();
        String cat = category.getSelectedItem().toString().trim();

        if(TextUtils.isEmpty(name)){
            e_name.setError("Please Enter Business Name");
            return false;
        }

        if(TextUtils.isEmpty(des)){
            e_description.setError("Please Enter Business Description");
            return false;
        }

        if(TextUtils.isEmpty(location)){
            e_location.setError("Please Enter Business Location");
            return false;
        }

        if(TextUtils.isEmpty(cat)){
            Toast.makeText(this,"Please Select a Category",Toast.LENGTH_LONG).show();
            return false;
        }

        //check for internet connection
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info == null || !info.isConnected()){
                Toast.makeText(this,"Please Check your Internet Connection",Toast.LENGTH_LONG).show();
                return false;
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
