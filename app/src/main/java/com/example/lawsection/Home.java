package com.example.lawsection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    TextView FullName;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    String userId;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recycler_view);
        Integer[] langLogo={R.drawable.civil_law,R.drawable.common_law,R.drawable.crime_law,R.drawable.statutory_law};
        String[] langName={"Civil Law","Common Law","Crime Law","Statutory Law"};
        mainModels=new ArrayList<>();
        for(int i=0;i<langLogo.length;i++){
            MainModel model=new MainModel(langLogo[i],langName[i]);
            mainModels.add(model);
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(Home.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainAdapter=new MainAdapter(Home.this, mainModels);
        recyclerView.setAdapter(mainAdapter);
        FullName=findViewById(R.id.profileFullName);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userId=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference=fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                FullName.setText(documentSnapshot.getString("fName"));
            }
        });
        drawerLayout=findViewById(R.id.drawer);
    }
    public  void ClickMenu(View view)
    {
        openDrawer (drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public  void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }
    public void ClickLaw(View view){
        redirectActivity(this,Lawsection.class);
    }
    public  void ClickRegister(View view){
        redirectActivity(this,CaseRegister.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aclass) {
        Intent intent=new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
    int counter=0;
    @Override
    public void onBackPressed() {
        counter++;
        if(counter==10)
            super.onBackPressed();
    }
}