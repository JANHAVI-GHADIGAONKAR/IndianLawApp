package com.example.lawsection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import static com.example.lawsection.Home.logout;
import static com.example.lawsection.Home.redirectActivity;

public class Lawsection extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView FullName;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ListView listView;
    DatabaseReference databaseReference;
    List<String> title_list,law_list;
    ArrayAdapter<String> adapter;
    Law law;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawsection);
        listView=findViewById(R.id.listView);
        databaseReference= FirebaseDatabase.getInstance().getReference("Law_Section");
        law=new Law();
        title_list=new ArrayList<>();
        law_list=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.item,R.id.item_txt,title_list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title_list.clear();
                law_list.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    law=ds.getValue(Law.class);
                    title_list.add(law.getTitle());
                    law_list.add(law.getLaw_info());

                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(Lawsection.this,Section.class);
                        String p=law_list.get(position);
                        intent.putExtra("key",p);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

}