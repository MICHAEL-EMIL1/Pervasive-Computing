package com.example.pervasivecomputingproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Actions {
    private int imageResId; // Resource ID of the image
    private String title;

    private Class<?> activityClass;



    public Actions(String title, int imageResId, Class<?> activityClass) {
        this.title = title;
        this.imageResId = imageResId;
        this.activityClass = activityClass;
    }


    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public Class<?> getActivityClass() {
        return activityClass;
    }
}