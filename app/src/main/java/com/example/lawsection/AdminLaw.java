package com.example.lawsection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminLaw extends AppCompatActivity {
    private Button btnSave;
    private EditText inputLaw,inputtitle;
    DatabaseReference ref;
    ListView listView;
    List<model> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_law);
        inputLaw=findViewById(R.id.inputLaw);
        inputtitle=findViewById(R.id.inputtitle);
        btnSave=findViewById(R.id.btnSave);
        ref= FirebaseDatabase.getInstance().getReference("Law_Section");
        listView=findViewById(R.id.listView);
        datalist=new ArrayList<>();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: " + inputLaw.getText().toString());
                String email = inputLaw.getText().toString().trim();
                String password = inputtitle.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    inputLaw.setError("Section information is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputtitle.setError("Title is Required.");
                    return;
                }
               addsection(inputLaw.getText().toString().trim(),inputtitle.getText().toString().trim());

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model model=datalist.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(AdminLaw.this);
                LinearLayout layout=new LinearLayout(AdminLaw.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText info=new EditText(AdminLaw.this);
                layout.addView(info);
                info.setHint("information");
                builder.setTitle("Update information").setMessage("Write information to update").setView(layout).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Law_Section").child(model.getId());
                        model model1=new model(model.getId(),info.getText().toString().trim(),model.getTitle());
                        reference.setValue(model1);

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Law_Section").child(model.getId());
                        reference.removeValue();
                    }
                }).show();

            }
        });


    }
private void addsection(String Law_info,String Title)
{
    String id=ref.push().getKey();
    model model=new model(id,Law_info,Title);
    ref.child(id).setValue(model);
}
    @Override
    protected void onStart() {
       super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datalist.clear();
               if(snapshot.exists())
                {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                       model model= dataSnapshot.getValue(model.class);
                       datalist.add(model);
                    }
                    MyViewHolder adapter=new MyViewHolder(AdminLaw.this,datalist);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}