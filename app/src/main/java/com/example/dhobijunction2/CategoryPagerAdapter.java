package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.dhobijunction2.priceEstimator.fragment.CategoryFragment;

import java.util.List;

public class CategoryPagerAdapter extends FragmentPagerAdapter {

    List<CategoryModel> categoryModels;

    public CategoryPagerAdapter(FragmentManager fragmentManager, @NonNull List<CategoryModel> list) {
        super(fragmentManager);
        this.categoryModels = list;
    }



    @Override
    public int getCount() {
        return categoryModels.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new CategoryFragment(categoryModels.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryModels.get(position).getTitle();
    }

}

