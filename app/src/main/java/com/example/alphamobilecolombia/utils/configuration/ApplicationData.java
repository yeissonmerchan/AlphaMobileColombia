package com.example.alphamobilecolombia.utils.configuration;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.apache.commons.io.FileUtils;

import java.io.File;

import static android.content.Context.ACTIVITY_SERVICE;

public class ApplicationData {
    public void Clear(Context context){

        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());

        File cache2 = context.getExternalCacheDir();
        File appDir2 = new File(cache2.getParent());

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

    public void ClearData(Context context){
        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            try {
                FileUtils.deleteQuietly(context.getCacheDir());
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static boolean deleteDirectory(File dir) {
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

}
