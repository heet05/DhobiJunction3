package com.example.dhobijunction2;

import android.net.Uri;

public class CustomModel {
    String imageName;
    Uri imageURI;

    public CustomModel() {
    }

    public CustomModel(String imageName, Uri imageURI) {
        this.imageName = imageName;
        this.imageURI = imageURI;
    }

    public String getImageName() {
        return imageName;
    }

    public Uri getImageURI() {
        return imageURI;
    }
}
