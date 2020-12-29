package com.example.dhobijunction2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class dashbordproductadpter extends FirestoreRecyclerAdapter<ProductModel,dashbordproductadpter.ProductViewHolder> {


    public dashbordproductadpter(Dashbord_ProductActivity dashbord_productActivity, FirestoreRecyclerOptions rvOptions) {
        super(rvOptions);
    }




    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
