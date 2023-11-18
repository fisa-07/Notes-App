package com.example.notes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.notesData.Data;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context context;
   private ArrayList<Data> dataArrayList;
    LayoutInflater layoutInflater;
    public CustomAdapter(Context context, ArrayList<Data> dataArrayList){
        this.context = context;
        this.dataArrayList = dataArrayList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return dataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.list_item,null);
        TextView note = (TextView) view.findViewById(R.id.note);
        TextView day = (TextView) view.findViewById(R.id.day);

        note.setText(dataArrayList.get(i).getNote());
        day.setText(dataArrayList.get(i).getDay());
        return view;
    }
}
