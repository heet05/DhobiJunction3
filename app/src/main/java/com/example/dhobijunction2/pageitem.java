package com.example.dhobijunction2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class pageitem {
    @SerializedName("banner_img")
    @Expose
    private final String banner_img;

    public pageitem(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getBanner_img() {
        return banner_img;
    }
}
