package com.example.lawsection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Admin extends AppCompatActivity implements View.OnClickListener  {
public CardView card1,card2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        card1=(CardView) findViewById(R.id.c1);
        card2=(CardView) findViewById(R.id.c2);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.c1:
                i=new Intent(this, AdminLaw.class);
                startActivity(i);
                break;
            case R.id.c2:
                i=new Intent(this, AdminCase.class);
                startActivity(i);
                break;
        }
    }
}