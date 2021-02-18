package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.OrderDetaliActivity;
import com.example.dhobijunction.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OrderDetaliAdapter extends FirestoreRecyclerAdapter<OrderModel,OrderDetaliAdapter.OrderDetailViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param orderDetaliActivity
     * @param options
     * @param detaliActivity
     */
    Context context;
    public OrderDetaliAdapter(OrderDetaliActivity orderDetaliActivity, @NonNull FirestoreRecyclerOptions<OrderModel> options, OrderDetaliActivity detaliActivity) {
        super(options);
        this.context=orderDetaliActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position, @NonNull OrderModel model) {

    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detali_list,parent,false);
        return new OrderDetailViewHolder(view);
    }

    public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titel,price;
        public OrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.Order_detail_image);
            titel=itemView.findViewById(R.id.Order_detail_title);
            price=itemView.findViewById(R.id.Order_detail_price);
        }
    }
}
