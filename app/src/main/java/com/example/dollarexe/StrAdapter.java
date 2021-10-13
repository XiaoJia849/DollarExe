package com.example.dollarexe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StrAdapter  extends ArrayAdapter<Country> {
    int resourceId;

    public StrAdapter(@NonNull Context context, int resource, @NonNull List<Country> objects) {
        super(context, resource, objects);
        resourceId=resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Country problem=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView name=view.findViewById(R.id.name);
        TextView country_rate=view.findViewById(R.id.country_rate);

        name.setText(problem.getName());
        country_rate.setText(problem.getRate());
        return view;

    }
}
