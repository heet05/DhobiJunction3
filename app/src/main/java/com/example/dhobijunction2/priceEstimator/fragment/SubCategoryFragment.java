package com.example.dhobijunction2.priceEstimator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhobijunction2.CategoryModel;
import com.example.dhobijunction2.CategoryPagerAdapter;
import com.example.dhobijunction2.ProductAdapter;
import com.example.dhobijunction2.ProductListAdapter;
import com.example.dhobijunction2.ProductModel;
import com.example.dhobijunction2.SubCategoryModel;
import com.example.dhobijunction2.databinding.FragmentSubcategoryBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryFragment extends Fragment {

    //private final String product_url = "http://dhobijunction.biz/api/product.php";
    private @NotNull
    FragmentSubcategoryBinding screen;
    private List<ProductModel> product_list =new ArrayList<>();
    private SubCategoryModel model;
    private ProductListAdapter productListAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProductAdapter adapter;
    public SubCategoryFragment(SubCategoryModel model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        screen = FragmentSubcategoryBinding.inflate(inflater, container, false);


        Query query = db.collection("product").whereEqualTo("Sid",model.getSid());
        FirestoreRecyclerOptions<ProductModel> rvOptions = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class).build();
        adapter = new ProductAdapter(getActivity(), rvOptions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        screen.rvProduct.setLayoutManager(layoutManager);
        screen.rvProduct.setAdapter(adapter);

        /*
        Query query=FirebaseFirestore.getInstance().collectionGroup("group");
        FirestoreRecyclerOptions<ProductModel> rvOptions=new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query,ProductModel.class).build();

                            adapter = new ProductAdapter(getActivity(), rvOptions);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            screen.rvProduct.setLayoutManager(layoutManager);
                            screen.rvProduct.setAdapter(adapter);
*/






        return screen.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}

        /*      StringRequest request = new StringRequest(Request.Method.POST, product_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.optJSONArray("product");
                if (jsonArray != null) {
                    product_list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<ProductModel>>() {
                    }.getType());
                    productListAdapter = new ProductListAdapter(getActivity(), product_list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    screen.rvProduct.setLayoutManager(layoutManager);
                    screen.rvProduct.setAdapter(productListAdapter);
                   // screen.rvProduct.setVisibility(product_list == null || product_list.size() < 1 ? View.VISIBLE : View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("subcat", model.getSid());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
        return screen.getRoot();
    }*/

