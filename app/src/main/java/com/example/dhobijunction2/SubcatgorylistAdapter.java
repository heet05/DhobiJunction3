package com.example.dhobijunction2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SubcatgorylistAdapter extends FirestoreRecyclerAdapter<SubCatgeory,SubcatgorylistAdapter.SubCatgeroyViewHolder> {


    public SubcatgorylistAdapter(Dashbord_SubcatgoryActivity dashbord_subcatgoryActivity, FirestoreRecyclerOptions rvOptions) {
        super(rvOptions);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubCatgeroyViewHolder holder, int position, @NonNull SubCatgeory model) {
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
                FirebaseFirestore.getInstance().collection("subcategory")
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

                FirebaseFirestore.getInstance().collection("subcategory")
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
    public SubCatgeroyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dash_subclist,parent,false);
        return new SubCatgeroyViewHolder(view);
    }

    public class SubCatgeroyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        EditText editText;
        ImageButton button,button1,button2;
        public SubCatgeroyViewHolder(@NonNull View itemView) {
            super(itemView);
            editText=itemView.findViewById(R.id.edt_Subcat);
            button=itemView.findViewById(R.id.dashSubclist_edit);
            textView=itemView.findViewById(R.id.SubclistText);
            button1=itemView.findViewById(R.id.dashSubclist_remove);
            button2=itemView.findViewById(R.id.dashSubclist_update);
        }
    }
}
