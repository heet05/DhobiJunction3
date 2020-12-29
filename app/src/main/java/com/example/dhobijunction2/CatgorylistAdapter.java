package com.example.dhobijunction2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.Any;
import com.google.protobuf.Internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatgorylistAdapter extends FirestoreRecyclerAdapter<Category,CatgorylistAdapter.CatgoryViewHolder> {

    public CatgorylistAdapter(Context context, FirestoreRecyclerOptions rvOptions) {
        super(rvOptions);
    }


    @Override
    protected void onBindViewHolder(@NonNull CatgorylistAdapter.CatgoryViewHolder holder, int position, @NonNull Category model) {
        holder.textView.setText(model.getTITLE());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editText.setVisibility(View.VISIBLE);
                holder.textView.setVisibility(View.GONE);
                holder.editText.setText(holder.textView.getText().toString());
                holder.button2.setVisibility(View.VISIBLE);
                holder.button.setVisibility(View.GONE);

            }
        });
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("category")
                        .whereEqualTo("TITLE",model.getTITLE())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error!=null)
                                    Log.d("UKJ",""+error.getMessage());
                                if (value!=null&&!value.isEmpty())
                                    value.getDocuments().get(0).getReference().delete();
                            }

                        });


            }
        });


        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> note=new HashMap<>();
                String title=holder.editText.getText().toString();
                note.put("TITLE",title);
                FirebaseFirestore.getInstance().collection("category")
                        .whereEqualTo("TITLE",model.getTITLE())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error!=null)
                                    Log.d("UKJ",""+error.getMessage());
                                if (value!=null&&!value.isEmpty())
                                    value.getDocuments().get(0).getReference().update(note);
                            }

                        });


            }
        });
    }

    @NonNull
    @Override
    public CatgorylistAdapter.CatgoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dash_clist,parent,false);
        return new CatgoryViewHolder(view);
    }

    public class CatgoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        EditText editText;
        ImageButton button,button1,button2;
        public CatgoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editText=itemView.findViewById(R.id.edt_cat);
            button=itemView.findViewById(R.id.dashclist_edit);
            textView=itemView.findViewById(R.id.clistText);
            button1=itemView.findViewById(R.id.dashclist_remove);
            button2=itemView.findViewById(R.id.dashclist_update);
        }
    }
}

