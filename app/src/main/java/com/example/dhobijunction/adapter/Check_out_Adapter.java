package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.Check_Out_Activity;
import com.example.dhobijunction.model.CheckModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Check_out_Adapter extends FirestoreRecyclerAdapter<CheckModel,Check_out_Adapter.CheckViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    Context context;
   Check_Out_Activity check_out_activity;

    public Check_out_Adapter(Context context, @NonNull FirestoreRecyclerOptions<CheckModel> options, Check_Out_Activity check_out_activity) {
        super(options);
        this.context=context;
        this.check_out_activity=check_out_activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull CheckViewHolder holder, int position, @NonNull CheckModel model) {
        holder.textView1.setText(model.getTitle());
        Glide.with(check_out_activity).load(model.getImage()).into(holder.imageView);
        holder.textView2.setText(" ₹ " + model.getTotal());


    }

    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.checkout_item1,parent,false);
        return new CheckViewHolder(view);
    }

    public class CheckViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1,textView2;
        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.check_image);
            textView1=itemView.findViewById(R.id.check_name);
            textView2=itemView.findViewById(R.id.Check_price);
        }
    }
}
