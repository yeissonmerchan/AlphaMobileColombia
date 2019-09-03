package com.example.alphamobilecolombia.utils.configuration.implement;

import android.os.Build;

import com.example.alphamobilecolombia.utils.configuration.IDevice;

public class Device implements IDevice {

    public String getVersionApi(){
        int version = Build.VERSION.SDK_INT;
        return String.valueOf(version);
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
