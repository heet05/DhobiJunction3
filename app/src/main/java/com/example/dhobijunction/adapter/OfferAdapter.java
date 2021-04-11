package com.example.dhobijunction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.OffersActivity;
import com.example.dhobijunction.model.offerModle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.payumoney.core.entity.PaymentEntity;
import com.payumoney.sdkui.presenter.fragmentPresenter.IRecyclerViewOnItemClickListener;
import com.payumoney.sdkui.ui.adapters.RecyclerViewAdapter;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {
Context context;
com.example.dhobijunction.model.offerModle offerModle;

    public OfferAdapter(OffersActivity offersActivity) {
        this.context=offersActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.offeractivity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseFirestore.getInstance().collection("offer").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                holder.title.setText(offerModle.getTitle());
                holder.code.setText(offerModle.getCode());
                holder.exdate.setText(offerModle.getDate());
                holder.price.setText(offerModle.getPrice());

            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,price,code,exdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.offer_title);
            price=itemView.findViewById(R.id.offer_price);
            code=itemView.findViewById(R.id.offer_code);
            exdate=itemView.findViewById(R.id.offer_exdate);
        }
    }
}
