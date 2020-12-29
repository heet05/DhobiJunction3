package com.example.dhobijunction2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private FragmentManager context;
    private List<pageitem> mSliderItems =new ArrayList<>();



    public SliderAdapterExample(FragmentManager childFragmentManager, ArrayList<pageitem> list) {
        this.context=childFragmentManager;
        this.mSliderItems=list;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagebanner,parent,false);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        pageitem sliderItem = mSliderItems.get(position);

       // viewHolder.textViewDescription.setText(sliderItem.getBanner_img());
        //viewHolder.textViewDescription.setTextSize(16);
        //viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getBanner_img())
                .fitCenter()
                .into(viewHolder.imageViewBackground);


    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

       // View itemView;
        ImageView imageViewBackground;
       // ImageView imageGifContainer;
        //TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.imageSlider);
          //  imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
        //    textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
        }
    }

}