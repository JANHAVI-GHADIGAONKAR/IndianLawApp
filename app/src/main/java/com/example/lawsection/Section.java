package com.example.lawsection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

public class Section extends AppCompatActivity {
    private TextView txt;
    DrawerLayout drawerLayout;
    TextView FullName;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        txt=(TextView)findViewById(R.id.next_txt);
        String t=getIntent().getStringExtra("key");
        txt.setText(t);
        FullName=findViewById(R.id.profileFullName);

        fAuth= FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
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
    public void ClickMenu(View view){
        Home.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        Home.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        Home.redirectActivity(this,Home.class);
    }
    public void ClickLaw(View view){
        Home.redirectActivity(this,Lawsection.class);
    }
    public void ClickRegister(View view){
        Home.redirectActivity(this,CaseRegister.class);
    }
    public void ClickLogout(View view){
        Home.logout(this);
    }
    protected void onPause(){
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
}