package com.example.alphamobilecolombia.data.remote.Enviroment;

import android.content.Context;
import android.widget.Switch;

import com.example.alphamobilecolombia.R;

public class ApiEnviroment {
    public static String GetIpAddressApi(String ApiType, Context context){
        String ipAddress;

        String currentlyEnviroment = context.getResources().getString(R.string.environment_release);

        if(currentlyEnviroment.contains("PRO")){
            switch (ApiType){
                case "autentication":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_autentication);;
                    break;
                case "storage":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_storage);;
                    break;
                case "generic":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_generic);;
                    break;
                default:
                    ipAddress = "";
                    break;
            }
        }
        else{
            switch (ApiType){
                case "autentication":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_autentication);;
                    break;
                case "storage":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_storage);;
                    break;
                case "generic":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_generic);;
                    break;
                default:
                    ipAddress = "";
                    break;
            }
        }
        return ipAddress;
    }
}
