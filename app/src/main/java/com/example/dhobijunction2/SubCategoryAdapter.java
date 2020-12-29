package com.example.dhobijunction2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dhobijunction2.priceEstimator.fragment.SubCategoryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;
public class SubCategoryAdapter extends FragmentPagerAdapter {

    private List<SubCategoryModel> models;
    private TabLayout layout;

    public SubCategoryAdapter(@NonNull FragmentManager fm, @NonNull List<SubCategoryModel> models, TabLayout layout) {
        super(fm);
        this.models = models;
        this.layout = layout;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new SubCategoryFragment(models.get(position));
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
         return models.get(position).getTitle();
    }

}
