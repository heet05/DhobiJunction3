package com.example.dhobijunction.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhobijunction.R;
import com.example.dhobijunction.activity.Check_Out_Activity;
import com.example.dhobijunction.activity.OffersActivity;
import com.example.dhobijunction.adapter.CartAdapter;
import com.example.dhobijunction.adapter.OnQtyUpdate;
import com.example.dhobijunction.model.CartModel;
import com.example.dhobijunction.model.OfferModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements OnQtyUpdate {
    RecyclerView recyclerView;
    CartAdapter adapter;
    CartModel model;
    OfferModel offerModel;
    SharedPreferences sharedPreferences;
    SharedPreferences pref;
    TextView t1, t6;
    EditText promo;

    Button button1,apply;
    String mobile = "";

    boolean isQtyUpdated;


    public CartFragment(CartModel CartModel) {
        this.model = CartModel;
    }

    public CartFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cart_recyclerview);
        promo =view.findViewById(R.id.cart_epromo);
        apply=view.findViewById(R.id.cart_bpromo);
        t1 = view.findViewById(R.id.cart_tv1);
        t6 = view.findViewById(R.id.cart_tv6);
        button1=view.findViewById(R.id.cart_Bt1);
       /* sharedPreferences=getContext().getSharedPreferences("Promocode",0);
        String  code=sharedPreferences.getString("code","");*/

        pref = getContext().getSharedPreferences("Users", 0);
        mobile = pref.getString("userMobile", "");
        Query query = FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART");
        FirestoreRecyclerOptions<CartModel> rvOptions = new FirestoreRecyclerOptions.Builder<CartModel>()
                .setQuery(query, CartModel.class).build();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(getActivity(), rvOptions, this);
        recyclerView.setAdapter(adapter);


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("offer").whereEqualTo("code",promo.getText().toString().trim()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value !=null && !value.isEmpty()){
                            for (int i=0;i<value.size();i++) {
                                OfferModel offerModel = value.getDocuments().get(i).toObject(OfferModel.class);

                                Integer finalTotal = Integer.parseInt(t6.getText().toString()) - Integer.parseInt(offerModel.getPrice());
                                t6.setText(String.valueOf(finalTotal));
                                Toast.makeText(getContext(), "code is vaild", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(getContext(), "code is wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OffersActivity.class);
                startActivity(intent);
            }
        });
        getCartTotal();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Check_Out_Activity.class);
                intent.putExtra("total",t6.getText().toString());
                startActivity(intent);
            }
        });
        return view;
    }


    private void getCartTotal() {
        FirebaseFirestore.getInstance().collection("USERS")
                .document(mobile).collection("USERCART").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    int total=0;

                    for (int i = 0; i < value.size(); i++) {
                      //  int sum=Integer.parseInt(value.getDocuments().get(i).get("price").toString()) * Integer.parseInt(value.getDocuments().get(i).get("qty").toString());
                        total += Integer.parseInt(value.getDocuments().get(i).get("total").toString());

                        t6.setText(String.valueOf(total));
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void getQty(String s, CartModel model) {
        final Map<String, Object> map = new HashMap<>();
        map.put("qty", s);
        map.put("total",String.valueOf(Integer.parseInt(s)*Integer.parseInt(model.getPrice())));
        FirebaseFirestore.getInstance().collection("USERS").document(pref.getString("userMobile", ""))
                .collection("USERCART").whereEqualTo("cartItemId", model.getCartItemId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null && !value.isEmpty()) {
                        value.getDocuments().get(0).getReference().update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                                        map.clear();
                                    }
                                else
                                    Toast.makeText(getActivity(), "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
                if (error != null) {
                    Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
