package com.example.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admin.Model.ProductModel;
import com.example.admin.R;
import com.example.admin.activity.ProductActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class productAdapter   extends RecyclerView.Adapter<productAdapter.Viewholder>{
    Context context;
    List<ProductModel> modelList;
    public productAdapter(ProductActivity productActivity, List<ProductModel> modelList) {
        this.context=productActivity;
        this.modelList=modelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new productAdapter.Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.title1.setText(modelList.get(position).getTitle());
        holder.price.setText(modelList.get(position).getPrice());
        Glide.with(context).load(modelList.get(position).getImage()).into(holder.imageView);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editText.setVisibility(View.VISIBLE);
                holder.pricetext.setVisibility(View.VISIBLE);
                holder.title1.setVisibility(View.GONE);
                holder.price.setVisibility(View.GONE);
                holder.edit.setVisibility(View.GONE);
                holder.update.setVisibility(View.VISIBLE);
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object> map=new HashMap<>();
                map.put("title",holder.editText.getText().toString());
                map.put("price",holder.pricetext.getText().toString());
                FirebaseFirestore.getInstance().collection("product").whereEqualTo("pid",modelList.get(position).getPid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (value !=null && !value.isEmpty()){
                            holder.update.setVisibility(View.GONE);
                            holder.price.setVisibility(View.VISIBLE);
                            holder.editText.setVisibility(View.GONE);
                            holder.pricetext.setVisibility(View.GONE);
                            holder.title1.setVisibility(View.VISIBLE);
                            value.getDocuments().get(0).getReference().update(map);
                            map.clear();
                        }
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("product").whereEqualTo("pid",modelList.get(position).getPid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value !=null&& !value.isEmpty()){

                            value.getDocuments().get(0).getReference().delete();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title1,price;
        EditText editText,pricetext;
        ImageView imageView;
        ImageButton edit,update,delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Product_iv);
            title1=itemView.findViewById(R.id.pr_title1);
            price=itemView.findViewById(R.id.Product_price);
            editText=itemView.findViewById(R.id.edittext);
            pricetext=itemView.findViewById(R.id.pricetext);
            edit=itemView.findViewById(R.id.pr_edit);
            update=itemView.findViewById(R.id.pr_update);
            delete=itemView.findViewById(R.id.pr_delete);
        }
    }
}
