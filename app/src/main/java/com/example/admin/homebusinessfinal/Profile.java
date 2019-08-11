package com.example.admin.homebusinessfinal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    //firebase Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    //other variables
    TextView e_name,e_no,e_address,e_des,e_email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        //initialize other variables
        e_name = findViewById(R.id.name_profile);
        e_des = findViewById(R.id.des_profile);
        e_address = findViewById(R.id.address_profile);
        e_no = findViewById(R.id.contact_number);
        e_email = findViewById(R.id.email_profile);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Profile Data");
        progressDialog.setMessage("Please wait...");
        progressDialog.setIcon(R.drawable.icon);

        //sign in if not already
        if(user==null){
            Intent intent = new Intent(Profile.this,Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            //start loading only if there is active internet connection
            //check for internet connection
            try{
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if(info == null || !info.isConnected()){
                    Toast.makeText(this,"Please Check your Internet Connection",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Profile.this,MainActivity.class);
                    startActivity(intent);
                }
            }catch(Exception e){
                e.printStackTrace();
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
            }
            progressDialog.show();
            loadData();
        }
    }

   //load the profile data from firebase
    private void loadData() {
        try{
            user = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
            Log.e("Check","Children: "+databaseReference);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name,contact,des,address,email;
                    try{
                        name = dataSnapshot.child("Name").getValue().toString().trim();
                        contact = dataSnapshot.child("Contact").getValue().toString().trim();
                        des = dataSnapshot.child("Description").getValue().toString().trim();
                        address = dataSnapshot.child("Address").getValue().toString().trim();
                        email = dataSnapshot.child("Email").getValue().toString().trim();
                        e_no.setText(contact);
                        e_des.setText(des);
                        e_name.setText(name);
                        e_address.setText(address);
                        e_email.setText(email);
                        progressDialog.dismiss();
                    }catch (Exception ne){
                        ne.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Could not load Personal Data due to system error",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Profile.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    //add a new business
    public void addBusiness(View view){
        Intent intent = new Intent(Profile.this,AddBusiness.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.item_editProfile:
                break;
            case R.id.item_viewBusiness:
                break;
            case R.id.item_signOut:
                firebaseAuth.signOut();
                Intent intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Profile.this,MainActivity.class);
        startActivity(intent);
    }
}