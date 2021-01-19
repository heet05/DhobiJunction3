

package com.example.dhobijunction2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dhobijunction2.databinding.FragmentCartBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    FragmentCartBinding screen;
    private List<Cart_model> Cart_list =new ArrayList<>();
    CartAdapter cartAdapter;
    SharedPreferences sp;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      screen=FragmentCartBinding.inflate(inflater,container,false);
        FirestoreRecyclerOptions<Cart_model> rvOptions = new FirestoreRecyclerOptions.Builder<Cart_model>().build();
       db.collection("USERS").document(sp.getString("userNumber","")).collection("CART").addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            value.getDocuments();
           }
       }) ;
      cartAdapter=new CartAdapter(getActivity(),rvOptions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        screen.rv.setLayoutManager(layoutManager);
        screen.rv.setAdapter(cartAdapter);


        return screen.getRoot();
    }
}
