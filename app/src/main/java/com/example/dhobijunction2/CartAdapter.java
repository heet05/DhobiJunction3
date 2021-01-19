package com.example.dhobijunction2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class CartAdapter extends FirestoreRecyclerAdapter<Cart_model,CartAdapter.ViewHolder> {
    FirestoreRecyclerOptions<Cart_model> rvOptions;
    FragmentActivity context;
    public CartAdapter(FragmentActivity context, FirestoreRecyclerOptions<Cart_model> options) {
        super(options);
        this.context=context;
        this.rvOptions=options;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Cart_model model) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
