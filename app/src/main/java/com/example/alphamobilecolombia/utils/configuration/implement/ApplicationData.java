package com.example.alphamobilecolombia.utils.configuration.implement;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import org.apache.commons.io.FileUtils;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;

public class ApplicationData {
    private void ClearCacheApp(Context context){
        try {
            File cache = context.getCacheDir();
            File appDir = new File(cache.getParent());

            if (appDir.exists()) {
                String[] children = appDir.list();
                for (String s : children) {
                    try {
                        if (!s.equals("lib")) {
                            deleteDirectory(new File(appDir, s));
                            Log.i("CleanData", "* File /data/data/APP_PACKAGE/" + s + " DELETED *");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"ClearCacheApp",ex,context);
        }
    }

    private void ClearUserData(Context context){
        try {
            File cache = context.getExternalCacheDir();
            File appDir = new File(cache.getParent());

            if (appDir.exists()) {
                String[] children = appDir.list();
                for (String s : children) {
                    try {
                        if (!s.equals("lib")) {
                            deleteDirectory(new File(appDir, s));
                            Log.i("CleanData", "* File /data/data/APP_PACKAGE/" + s + " DELETED *");
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"ClearUserData",ex,context);
        }
    }

    private void ClearData(Context context){
        try {
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                try {
                    FileUtils.deleteQuietly(context.getCacheDir());
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex){
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"ClearData",ex,context);
        }
    }

    private static boolean deleteDirectory(File dir) {
        try{
            if (dir != null && dir.isDirectory()) {
                String[] children = dir.list();
                int i = 0;
                while (i < children.length) {
                    boolean success = deleteDirectory(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                    i++;
                }
            }
            assert dir != null;
            return dir.delete();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void RestartNewVersionApp(Context context){
        ClearCacheApp(context);
        ClearUserData(context);
        ClearData(context);
    }

    private void ClearDirectoryTemp(Context context){
        ClearUserData(context);
    }

}
