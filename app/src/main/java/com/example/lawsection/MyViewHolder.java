package com.example.lawsection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyViewHolder extends ArrayAdapter<model> {
    TextView textViewTitle,textViewinfo;
    private Activity context;
    private List<model> modelList;

    public MyViewHolder(Activity context,List<model> modelList) {
        super(context,R.layout.single_view_layout,modelList);
        this.context=context;
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View ListItem=inflater.inflate(R.layout.single_view_layout,null,true);
        TextView Title=ListItem.findViewById(R.id.textViewTitle);
        TextView Law_info=ListItem.findViewById(R.id.textViewinfo);
        model model= modelList.get(position);

        Title.setText("Title: "+model.getTitle());
        Law_info.setText("Law_info: "+model.getLaw_info());
        return ListItem;
    }
}
