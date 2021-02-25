package com.example.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.example.admin.activity.OrderdetailActivity;

public class orderDetaliAdapter extends RecyclerView.Adapter<orderDetaliAdapter.ViewHolder>{
    Context context;
    public orderDetaliAdapter(OrderdetailActivity orderdetailActivity) {
     this.context=orderdetailActivity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_main, parent, false);
        return new orderDetaliAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,total;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
