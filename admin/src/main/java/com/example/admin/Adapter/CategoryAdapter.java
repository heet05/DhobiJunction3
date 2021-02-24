package com.example.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.categoryModel;
import com.example.admin.R;
import com.example.admin.activity.CatagroyActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<categoryModel>list;
    Context context;
    public CategoryAdapter(CatagroyActivity catagroyActivity, List<categoryModel> list) {
    this.context=catagroyActivity;
    this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getTITLE());
        holder.ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editText.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.GONE);
                holder.ib1.setVisibility(View.GONE);
                holder.ib2.setVisibility(View.VISIBLE);
            }
        });
        holder.ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String,Object>map=new HashMap<>();
                map.put("TITLE",holder.editText.getText().toString());
                FirebaseFirestore.getInstance().collection("category").whereEqualTo("Cid",list.get(position).getCid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (value !=null && !value.isEmpty()){
                            holder.ib2.setVisibility(View.GONE);

                            holder.editText.setVisibility(View.GONE);
                            holder.textView.setVisibility(View.VISIBLE);
                            holder.ib2.setVisibility(View.GONE);
                           value.getDocuments().get(0).getReference().update(map);
                        }
                    }
                });
            }
        });
        holder.ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              categoryModel categoryModel=new categoryModel();
                FirebaseFirestore.getInstance().collection("category").whereEqualTo("Cid",list.get(position).getCid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        EditText  editText;
        ImageButton ib1,ib2,ib3;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView=itemView.findViewById(R.id.t1);
            editText=itemView.findViewById(R.id.e1);
            ib1=itemView.findViewById(R.id.i1);
            ib2=itemView.findViewById(R.id.i2);
            ib3=itemView.findViewById(R.id.i3);
        }
    }
}
