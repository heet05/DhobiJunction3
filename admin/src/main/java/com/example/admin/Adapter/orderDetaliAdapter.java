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
import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.example.admin.activity.OrderdetailActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class orderDetaliAdapter extends FirestoreRecyclerAdapter<orderModel, orderDetaliAdapter.ViewHolde> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;

    public orderDetaliAdapter(OrderdetailActivity orderdetailActivity, @NonNull FirestoreRecyclerOptions<orderModel> options, OrderdetailActivity activity) {
        super(options);
        this.context = orderdetailActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolde holder, int position, @NonNull orderModel model) {
holder.title.setText(model.getModelList().get(position).getTitle());
holder.total.setText(model.getModelList().get(position).getTotal());
holder.price.setText(model.getModelList().get(position).getPrice());
holder.qty.setText(model.getModelList().get(position).getQty());
        Glide.with(context).load(model.getModelList().get(position).getImage()).into(holder.imageView);

    }

    @NonNull
    @Override
    public ViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_detail_main, parent, false);
        return new ViewHolde(view);
    }

    public class ViewHolde extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, qty, price, total;

        public ViewHolde(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title1);
            qty = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);

        }
    }
}
