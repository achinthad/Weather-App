package com.example.weatherapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemname;
    private final String[] monthno;
    private final String[] descrption;
    private final Integer[] imgid;


    public CustomListAdapter(Activity context, String[] itemname, String[] monthno, Integer[] imgid, String[] descrption) {
        super(context, R.layout.mylist, itemname);
        this.context = context;
        this.itemname = itemname;
        this.monthno = monthno;
        this.imgid = imgid;
        this.descrption = descrption;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.dayName);
        TextView txtTitle_1 = (TextView) rowView.findViewById(R.id.tempValue);
        TextView txtTitle_2 = (TextView) rowView.findViewById(R.id.desc);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(itemname[position]);
        txtTitle_1.setText(monthno[position]);
        txtTitle_2.setText(descrption[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;
    };
}



