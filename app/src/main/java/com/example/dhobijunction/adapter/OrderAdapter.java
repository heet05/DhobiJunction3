package com.example.dhobijunction.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.OrderDetaliActivity;
import com.example.dhobijunction.fragment.OrderFragment;
import com.example.dhobijunction.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrderModel,OrderAdapter.OrderViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param activity
     * @param options
     * @param orderFragment
     */
    Context context;
    public OrderAdapter(FragmentActivity activity, @NonNull FirestoreRecyclerOptions<OrderModel> options, OrderFragment orderFragment) {
        super(options);
        this.context=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderModel model) {
        holder.textView.setText(model.getTotal());
        holder.date.setText(String.valueOf(model.getTimestamp()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetaliActivity.class);
                intent.putExtra("Model",OrderModel.class);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oder_list_view, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textView,date;
        LinearLayout linearLayout;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.Order_date);
            textView=itemView.findViewById(R.id.Order_total);
            linearLayout=itemView.findViewById(R.id.Order_click);
        }
    }
}
