package com.example.pervasivecomputingproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DataUtils {
    public static ArrayList<Actions> getActionsData(Context context) {
        ArrayList<Actions> arr = new ArrayList<>();
        arr.add(new Actions("Temperature", R.drawable.temp, Temperature.class));
        arr.add(new Actions("LED", R.drawable.led, Led.class));
        arr.add(new Actions("keypad", R.drawable.keypad, Keypad.class));
        arr.add(new Actions("Message on LCD", R.drawable.lcd, LCD.class));
        return arr;
    }
}
