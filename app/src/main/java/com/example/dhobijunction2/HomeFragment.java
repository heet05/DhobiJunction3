package com.example.dhobijunction2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dhobijunction2.priceEstimator.PriceEstimatorActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Button button;
    SliderView sliderView;
    ArrayList<pageitem>list=new ArrayList();
    String category = "http://dhobijunction.biz/api/banner.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home,container,false);
        sliderView=v.findViewById(R.id.view_flipper);
        StringRequest request = new StringRequest(Request.Method.GET, category, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("banner");
                list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<pageitem>>() {
                }.getType());
                SliderAdapterExample adpter= new SliderAdapterExample(getChildFragmentManager(), list);
                sliderView.setSliderAdapter(adpter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);


        button=v.findViewById(R.id.home_btn);
        if (new ConnectionDetector(getActivity()).isConnectingToInternet()){

        }
        else{
            new ConnectionDetector(getActivity()).connectiondetect();
        }
        getActivity().setTitle("dhobi Junction ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), PriceEstimatorActivity.class);
                startActivity(intent);
            }
        });
        return v;

    }
}
