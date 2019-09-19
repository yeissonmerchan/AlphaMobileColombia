package com.example.alphamobilecolombia.configuration.environment;

import android.content.Context;

import com.example.alphamobilecolombia.R;

public class ApiEnviroment {
    public static String GetIpAddressApi(String ApiType, Context context){
        String ipAddress = null;

        String currentlyEnviroment = context.getResources().getString(R.string.environment_release);

        if(currentlyEnviroment.contains("PRO")){
            switch (ApiType){
                case "autentication":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_authentication);
                    break;
                case "storage":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_storage);
                    break;
                case "generic":
                    ipAddress = context.getResources().getString(R.string.pro_ip_api_generic);
                    break;
                default:
                    ipAddress = "";
                    break;
            }
        }
        else if(currentlyEnviroment.contains("PRE")){
            switch (ApiType){
                case "autentication":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_authentication);
                    break;
                case "storage":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_storage);
                    break;
                case "generic":
                    ipAddress = context.getResources().getString(R.string.pre_ip_api_generic);
                    break;
                default:
                    ipAddress = "";
                    break;
            }
        }
        else if(currentlyEnviroment.contains("DEV")){
            switch (ApiType){
                case "autentication":
                    ipAddress = context.getResources().getString(R.string.dev_ip_api_authentication);;
                    break;
                case "storage":
                    ipAddress = context.getResources().getString(R.string.dev_ip_api_storage);;
                    break;
                case "generic":
                    ipAddress = context.getResources().getString(R.string.dev_ip_api_generic);;
                    break;
                default:
                    ipAddress = "";
                    break;
            }
        }
        return ipAddress;
    }
}

