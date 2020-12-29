package com.example.dhobijunction2.priceEstimator;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.dhobijunction2.CategoryModel;
import com.example.dhobijunction2.CategoryPagerAdapter;
import com.example.dhobijunction2.databinding.ActivityCatgoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PriceEstimatorActivity extends AppCompatActivity {

    ActivityCatgoryBinding screen;
    TabLayout tabLayout;
    ViewPager viewPager;

    List<CategoryModel> list;
    String category = "http://dhobijunction.biz/api/category.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen = ActivityCatgoryBinding.inflate(getLayoutInflater());
        setContentView(screen.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Products");
        getSupportActionBar().setElevation(0f);
        System.setProperty("http.keepAlive", "false");
        tabLayout = screen.tabLayout;
        viewPager = screen.vPager;
        list = new ArrayList<CategoryModel>();


        FirebaseFirestore.getInstance().collection("category").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                   CategoryModel categoryModel = documentSnapshot.toObject(CategoryModel.class);

                    categoryModel.setId(documentSnapshot.getString("ID"));
                    categoryModel.setTitle(documentSnapshot.getString("TITLE"));
                    list.add(categoryModel);
                }
                CategoryPagerAdapter adapter = new CategoryPagerAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(adapter);
               // screen.tabLayout.setTabMode(list.size() > 3 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
                tabLayout.setupWithViewPager(viewPager);
            }
        });
                /*
        StringRequest request = new StringRequest(Request.Method.POST, category, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("category");
                list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<CategoryModel>>() {
                }.getType());
                CategoryPagerAdapter adapter = new CategoryPagerAdapter(getSupportFragmentManager(), list);
                viewPager.setAdapter(adapter);
              //  screen.tabLayout.setTabMode(list.size() > 3 ? TabLayout.MODE_SCROLLABLE : TabLayout.MODE_FIXED);
                tabLayout.setupWithViewPager(viewPager);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(PriceEstimatorActivity.this);
        requestQueue.add(request);

 */
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
