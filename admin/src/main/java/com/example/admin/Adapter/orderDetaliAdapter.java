package com.example.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin.Model.checkOutModel;
import com.example.admin.R;
import com.example.admin.activity.OrderdetailActivity;

import java.util.List;

public class orderDetaliAdapter extends RecyclerView.Adapter<orderDetaliAdapter.ViewHolde> {
    List<checkOutModel> list;
Context context;

    public orderDetaliAdapter(OrderdetailActivity orderdetailActivity, List<checkOutModel> modelList) {
        this.context=orderdetailActivity;
       this.list=modelList;
    }


    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail_main, parent, false);
        return new ViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.price.setText(list.get(position).getPrice());
        holder.qty.setText(list.get(position).getQty());
        Glide.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.total.setText(list.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, qty, price, total;

        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title1);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);

        }
    }
}
