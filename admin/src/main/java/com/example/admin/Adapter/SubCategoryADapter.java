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

import com.example.admin.Model.SubCategoryModel;
import com.example.admin.Model.categoryModel;
import com.example.admin.R;
import com.example.admin.activity.Sub_CagtegoryActivity;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;

public class SubCategoryADapter  extends RecyclerView.Adapter<SubCategoryADapter.VieWholder> {
Context context;
List<SubCategoryModel> modelList;
    public SubCategoryADapter(Sub_CagtegoryActivity sub_cagtegoryActivity, List<SubCategoryModel> modelList) {
    this.context=sub_cagtegoryActivity;
    this.modelList=modelList;
    }

    @NonNull
    @Override
    public VieWholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.subcategory_item,parent,false);
        return new VieWholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VieWholder holder, int position) {
        holder.textView.setText(modelList.get(position).getTITLE());
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

                HashMap<String,Object> map=new HashMap<>();
                map.put("TITLE",holder.editText.getText().toString());
                FirebaseFirestore.getInstance().collection("subcategory").whereEqualTo("sid",modelList.get(position).getSid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                FirebaseFirestore.getInstance().collection("subcategory").whereEqualTo("sid",modelList.get(position).getSid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    public class VieWholder extends RecyclerView.ViewHolder {
        TextView textView;
        EditText editText;
        ImageButton ib1,ib2,ib3;
        public VieWholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.t1);
            editText=itemView.findViewById(R.id.e1);
            ib1=itemView.findViewById(R.id.i1);
            ib2=itemView.findViewById(R.id.i2);
            ib3=itemView.findViewById(R.id.i3);
        }
    }
}
