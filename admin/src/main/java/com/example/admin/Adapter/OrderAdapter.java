package com.example.admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.example.admin.activity.OrderActivity;
import com.example.admin.activity.OrderdetailActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;

public class OrderAdapter extends FirestoreRecyclerAdapter<orderModel,OrderAdapter.ViewHolder>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param orderActivity
     * @param options
     * @param activity
     */
    Context context;

    public OrderAdapter(OrderActivity orderActivity, @NonNull FirestoreRecyclerOptions<orderModel> options, OrderActivity activity) {
        super(options);
        this.context=orderActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull orderModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        holder.textView.setText(model.getTotal());
        holder.date.setText(sdf.format(model.getTimestamp()));
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());
        holder.payment.setText(model.getPayMentMethod());
        holder.number.setText(model.getNumber());

        holder.name.setText(model.getName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,OrderdetailActivity.class);
                intent.putExtra("Model",model);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, date,name,number,address,email,payment;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Order_name);
            number=itemView.findViewById(R.id.Order_number);
            payment=itemView.findViewById(R.id.Order_payment);
            address=itemView.findViewById(R.id.Order_address);
            email=itemView.findViewById(R.id.Order_email);
            date = itemView.findViewById(R.id.Order_date);
            textView = itemView.findViewById(R.id.Order_total);
            linearLayout = itemView.findViewById(R.id.Order_click);
        }
    }
}
