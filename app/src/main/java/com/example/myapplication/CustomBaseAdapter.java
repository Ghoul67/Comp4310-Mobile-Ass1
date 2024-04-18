package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;

public class CustomBaseAdapter extends BaseAdapter {

    private Context cxt;
    private String[] names;
    private String[] contents;
    private String[] colors;

    LayoutInflater layoutInflater;
    public CustomBaseAdapter(Context cxt, String[] names, String[] contents, String[] colors) {
        this.cxt = cxt;
        this.names = names;
        this.contents = contents;
        this.colors = colors;
        layoutInflater = LayoutInflater.from(cxt);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.note_view, null);
        TextView txtCourseName = convertView.findViewById(R.id.txtCourseName);
        TextView txtContent = convertView.findViewById(R.id.txtContent);
        LinearLayoutCompat noteLayout = convertView.findViewById(R.id.noteContainer);

        txtCourseName.setText(names[position]);
        txtContent.setText(contents[position]);
        noteLayout.setBackgroundColor(Color.parseColor(colors[position]));
        return convertView;
    }
}
