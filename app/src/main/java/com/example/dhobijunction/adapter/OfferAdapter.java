package com.example.dhobijunction.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.OffersActivity;
import com.example.dhobijunction.model.OfferModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OfferAdapter extends FirestoreRecyclerAdapter<OfferModel, OfferAdapter.ViewHolder> {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public OfferAdapter(OffersActivity offersActivity, FirestoreRecyclerOptions<OfferModel> rvOptions, OffersActivity offersActivity1) {
        super(rvOptions);
        this.context = offersActivity;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull OfferModel model) {
        holder.title.setText(model.getTitle());
        holder.code.setText(model.getCode());
        holder.price.setText(model.getPrice());
   /* holder.button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sharedPreferences = context.getSharedPreferences("Promocode", 0);
            editor = sharedPreferences.edit();
            editor.putString("code", model.getCode());
            editor.apply();

        }
    });*/

        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offeractivity, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, code, price, date;
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.copy);
            title = itemView.findViewById(R.id.offer_title);
            code = itemView.findViewById(R.id.offer_code);
            price = itemView.findViewById(R.id.offer_price);
            date = itemView.findViewById(R.id.offer_exdate);
        }
    }
}
