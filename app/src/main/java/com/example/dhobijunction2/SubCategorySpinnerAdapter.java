package com.example.dhobijunction2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SubCategorySpinnerAdapter extends BaseAdapter {
    Context context;
    List<SubCatgeory> titleList;

    public SubCategorySpinnerAdapter(Dashbord_ProductActivity dashbord_productActivity, List<SubCatgeory> titleList) {
        this.context=dashbord_productActivity;
        this.titleList=titleList;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view,parent,false);
        TextView textView=convertView.findViewById(R.id.tv_item);
        textView.setText(titleList.get(position).getTITLE());
        return convertView;
    }
}
