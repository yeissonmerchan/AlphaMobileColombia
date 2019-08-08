package com.example.alphamobilecolombia.utils.configuration;

import android.content.Context;
import android.util.Log;

import java.io.File;

public class ApplicationData {
    public void Clear(Context context){
        File cache = context.getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDirectory(new File(appDir, s));
                    Log.i("CleanData", "* File /data/data/APP_PACKAGE/" + s + " DELETED *");
                }
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
