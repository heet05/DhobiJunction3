package com.example.dhobijunction2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ProductAdapter extends FirestoreRecyclerAdapter<ProductModel, ProductAdapter.ProductViewHolder> {
    FirestoreRecyclerOptions<ProductModel> rvOptions;
    OnAddCartListener listener;
    FragmentActivity subCategoryFragment;

    public ProductAdapter(FragmentActivity subCategoryFragment, FirestoreRecyclerOptions<ProductModel> rvOptions, OnAddCartListener listener) {
        super(rvOptions);
        this.subCategoryFragment = subCategoryFragment;
        this.listener = listener;

        this.rvOptions = rvOptions;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addToCart(model, holder.total_items.getText().toString());
            }
        });
        holder.tv_item.setText(model.getTitle());
        holder.tv_item_price.setText(model.getPrice());

        Glide.with(subCategoryFragment).load(model.getImage()).into(holder.imageView);
        holder.total_price.setText("\u20B9 " + model.getPrice());
        holder.imgbtn_remove.setOnClickListener(view -> {
            int count = Integer.parseInt(String.valueOf(holder.total_items.getText()));
            if (count > 1) {
                count--;
                holder.total_items.setText(String.valueOf(count));
                holder.total_price.setText("\u20B9 " + (count * Integer.parseInt(model.getPrice())));
            }
        });
        holder.imgbtn_add.setOnClickListener(v -> {
            int count = Integer.parseInt(String.valueOf(holder.total_items.getText()));
            count++;
            holder.total_items.setText(String.valueOf(count));
            holder.total_price.setText("\u20B9 " + (count * Integer.parseInt(model.getPrice())));
        });

        //  if (position % 2 == 1)
        //   holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.odd));
        //else
        //  holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.even));

        holder.tv_kg.setText("  Per " + model.getKgGm());

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductAdapter.ProductViewHolder(view);

    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_item, tv_item_price, total_items, total_price, tv_kg;
        ImageButton imgbtn_remove, imgbtn_add;
        Button btn_add;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_kg = itemView.findViewById(R.id.pricePerKg);
            imageView = itemView.findViewById(R.id.item_img);
            tv_item = itemView.findViewById(R.id.tv_item);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            total_price = itemView.findViewById(R.id.tv_total_price);
            total_items = itemView.findViewById(R.id.total_items);
            imgbtn_remove = itemView.findViewById(R.id.imgbtn_remove);
            imgbtn_add = itemView.findViewById(R.id.imgbtn_add);
            btn_add = itemView.findViewById(R.id.btn_add);
        }
    }


}
