package com.example.dhobijunction2.priceEstimator;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction2.Category;
import com.example.dhobijunction2.Dashbord_SubcatgoryActivity;
import com.example.dhobijunction2.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class CategorySpinnerAdapter extends BaseAdapter {
    Context context;
    List<Category> titleList;
    public CategorySpinnerAdapter(Context context, List<Category> titleList) {
        this.context=context;
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
        convertView =LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view,parent,false);
        TextView textView=convertView.findViewById(R.id.tv_item);
        textView.setText(titleList.get(position).getTITLE());
        return convertView;
    }
}
