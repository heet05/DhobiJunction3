package com.example.dhobijunction2.priceEstimator.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhobijunction2.CategoryModel;
import com.example.dhobijunction2.CategoryPagerAdapter;
import com.example.dhobijunction2.SubCategoryAdapter;
import com.example.dhobijunction2.SubCategoryModel;
import com.example.dhobijunction2.databinding.FragmentCategoryBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryFragment extends Fragment {

    String subcat_url = "http://dhobijunction.biz/api/sub_category.php";
    SubCategoryAdapter adapter;
    private FragmentCategoryBinding screen;
    private List<SubCategoryModel> list =new ArrayList<>();
    private CategoryModel model;

    public CategoryFragment(CategoryModel model) {
        this.model = model;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        screen = FragmentCategoryBinding.inflate(inflater, container, false);
        if (list.size()==0) {

            FirebaseFirestore.getInstance().collection("subcategory")
                    .whereEqualTo("Cid", model.getId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        return;
                    }
                    String data = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        SubCategoryModel subCategoryModel = documentSnapshot.toObject(SubCategoryModel.class);

                        subCategoryModel.setSid(documentSnapshot.getString("Sid"));
                        subCategoryModel.setTitle(documentSnapshot.getString("TITLE"));
                        list.add(subCategoryModel);
                    }
                    adapter = new SubCategoryAdapter(getChildFragmentManager(), list, screen.tabLayout1);
                    screen.vPager.setAdapter(adapter);
                    screen.tabLayout1.setupWithViewPager(screen.vPager);

                }
            });}
        else{
            adapter = new SubCategoryAdapter(getChildFragmentManager(), list, screen.tabLayout1);
            screen.vPager.setAdapter(adapter);
            screen.tabLayout1.setupWithViewPager(screen.vPager);}
        /*        StringRequest request = new StringRequest(Request.Method.POST, subcat_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("subcat");
                list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<SubCategoryModel>>() {
                }.getType());
                adapter = new SubCategoryAdapter(getChildFragmentManager(), list, screen.tabLayout1);
                screen.vPager.setAdapter(adapter);
               // screen.tabLayout1.setTabMode(list.size() > 4 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
                screen.tabLayout1.setupWithViewPager(screen.vPager);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("category_id", model.getId());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
*/
        return screen.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("FragmentLaundry", "onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }
}