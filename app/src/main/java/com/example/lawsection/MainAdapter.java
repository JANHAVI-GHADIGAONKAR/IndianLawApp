package com.example.lawsection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
ArrayList<MainModel> mainModels;
Context context;
    ArrayList<String> recordings = null;

    public MainAdapter(Context context, ArrayList<MainModel> mainModels){
    this.context=context;
    this.mainModels=mainModels;

}


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(mainModels.get(position).getLangLogo());
        holder.textView.setText(mainModels.get(position).getLangName());
holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(mainModels.get(position).getLangLogo().equals(1)){
            Intent gotoactivity1=new Intent(context,civil_law.class);
            context.startActivity(gotoactivity1);
        }
        else if(mainModels.get(position).getLangLogo()==1){
            Intent gotoactivity2=new Intent(context,common_law.class);
            context.startActivity(gotoactivity2);
        }
        final Intent intent;
        switch (position)
        {
            case 0:
                intent = new Intent(context, civil_law.class);
                break;

            case 1:
                intent =  new Intent(context, common_law.class);
                break;
            case 2:
                intent =  new Intent(context, criminal_law.class);
                break;
            default:
                intent =  new Intent(context, MainActivity.class);
                break;
        }

        context.startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView textView;
LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.image_view);
            textView=itemView.findViewById(R.id.text_view);
            linearLayout=itemView.findViewById(R.id.linear_layout);
        }



    }
}
