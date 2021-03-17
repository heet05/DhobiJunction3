package com.example.admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.Model.categoryModel;
import com.example.admin.Model.orderModel;
import com.example.admin.R;
import com.example.admin.activity.DeliveryModel;
import com.example.admin.activity.OrderActivity;
import com.example.admin.activity.OrderdetailActivity;
import com.example.admin.activity.Sub_CagtegoryActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAdapter extends FirestoreRecyclerAdapter<orderModel,OrderAdapter.ViewHolder>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param orderActivity
     * @param options
     * @param activity
     */
    Context context;
    List<DeliveryModel> list;
    List<String> deliveryList = new ArrayList<>();
    DeliveryModel deliveryModel1;
    public OrderAdapter(OrderActivity orderActivity, @NonNull FirestoreRecyclerOptions<orderModel> options, OrderActivity activity) {
        super(options);
        this.context=orderActivity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull orderModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        holder.textView.setText(model.getTotal());
        holder.date.setText(sdf.format(model.getTimestamp()));
        holder.email.setText(model.getEmail());
        holder.address.setText(model.getAddress());
        holder.payment.setText(model.getPayMentMethod());
        holder.number.setText(model.getNumber());

        holder.name.setText(model.getName());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,OrderdetailActivity.class);
                intent.putExtra("Model",model);
                context.startActivity(intent);
            }
        });
      FirebaseFirestore.getInstance().collection("deliveryboy").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value !=null && !value.isEmpty()) {
                    list =value.toObjects(DeliveryModel.class);
                    for (int i=0;i<list.size();i++){

                        deliveryList.add(list.get(i).getName());


                    }


                    ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(context,R.layout.support_simple_spinner_dropdown_item,deliveryList);
                   holder.spinner.setAdapter(arrayAdapter);

                }
            }
        });
      holder.imageButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FirebaseFirestore.getInstance().collection("deliveryboy" ).document().collection("Order").add(model).addOnSuccessListener(documentReference -> {
                  String docId = documentReference.getId();
                  Map<String, Object> map = new HashMap<>();
                  map.put("oid", docId);
                  documentReference.update(map).addOnSuccessListener(aVoid -> {
                      Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                  }).addOnFailureListener(e -> {
                      Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                  });
              }).addOnFailureListener(e -> {
                  Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

              });
          }
      });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, date,name,number,address,email,payment;
        LinearLayout linearLayout;
        Spinner spinner;
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Order_name);
            number=itemView.findViewById(R.id.Order_number);
            payment=itemView.findViewById(R.id.Order_payment);
            address=itemView.findViewById(R.id.Order_address);
            email=itemView.findViewById(R.id.Order_email);
            date = itemView.findViewById(R.id.Order_date);
            textView = itemView.findViewById(R.id.Order_total);
            linearLayout = itemView.findViewById(R.id.Order_click);
            imageButton=itemView.findViewById(R.id.delvered);
            spinner=itemView.findViewById(R.id.sp1);

        }
    }
}
