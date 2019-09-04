package com.example.admin.homebusinessfinal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.homebusinessfinal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import SupportClasses.BusinessData;
import SupportClasses.BusinessListAdapter;
import SupportClasses.ListAdapter;

public class MainActivity extends AppCompatActivity {

    //firebase Variables
    private DatabaseReference databaseReference;

    List<BusinessData> desserts = new ArrayList<BusinessData>();
    List<BusinessData> fashion = new ArrayList<BusinessData>();
    List<BusinessData> tiffin = new ArrayList<BusinessData>();
    List<BusinessData> education = new ArrayList<BusinessData>();
    List<BusinessData> art = new ArrayList<BusinessData>();
    List<BusinessData> others = new ArrayList<BusinessData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //intialize up firebase variables
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Loading Data");
        progressDialog.setIcon(R.drawable.icon);
        progressDialog.show();

        //load dessert businesses
        Query query = databaseReference.child("Desserts and Cakes").orderByChild("Name").limitToLast(2);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    desserts.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_dessert);
                BusinessListAdapter adapter = new BusinessListAdapter(desserts);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //load fashion related businesses
        Query query1 = databaseReference.child("Fashion and clothing").orderByChild("Name").limitToLast(2);
        query1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    fashion.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_fashion);
                BusinessListAdapter adapter = new BusinessListAdapter(fashion);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //load educational services
        Query query2 = databaseReference.child("Educational Services").orderByChild("Name").limitToLast(2);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    education.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_education);
                BusinessListAdapter adapter = new BusinessListAdapter(education);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //load tiffin services
        query2 = databaseReference.child("Food and Tiffin Services").orderByChild("Name").limitToLast(2);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    tiffin.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_tiffin);
                BusinessListAdapter adapter = new BusinessListAdapter(tiffin);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //load art
        query2 = databaseReference.child("Art and Craft").orderByChild("Name").limitToLast(2);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    art.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_art);
                BusinessListAdapter adapter = new BusinessListAdapter(art);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //load others
        query2 = databaseReference.child("Others").orderByChild("Name").limitToLast(2);
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BusinessData data = dataSnapshot.getValue(BusinessData.class);
                if(data!=null){
                    others.add(0,data);
                }else{
                    Toast.makeText(getApplicationContext(),"Problem Loading data...",Toast.LENGTH_LONG).show();
                }
                RecyclerView rvBusinessData = findViewById(R.id.recycler_others);
                BusinessListAdapter adapter = new BusinessListAdapter(others);
                rvBusinessData.setAdapter(adapter);
                rvBusinessData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query2.keepSynced(true);
    }

    //add business
    public void addBusiness(View view){
        Intent intent = new Intent(MainActivity.this,AddBusiness.class);
        startActivity(intent);
    }

    //display the profile icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handling click on profile icon
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,Profile.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
