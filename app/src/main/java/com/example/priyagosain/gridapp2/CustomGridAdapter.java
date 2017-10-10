package com.example.priyagosain.gridapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by priyagosain on 2017-10-06.
 */

public class CustomGridAdapter extends BaseAdapter
{
    private Context contxt;
    private String[] items;
    LayoutInflater inflater;

    public CustomGridAdapter(Context contxt, String[] items){
        this.contxt = contxt;
        this.items = items;
        inflater = (LayoutInflater) this.contxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return items.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.cell,null);}

        TextView textview = (TextView) view.findViewById(R.id.textview);
        textview.setText(items[i]);
        return view;
    }
}