package com.example.alphamobilecolombia.utils.files.implement;

import android.content.Context;
import android.util.Base64;

import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.files.IFileStorage;

import java.io.File;
import java.io.FileInputStream;

public class FileStorage implements IFileStorage {
    String _pathFile;
    Context _context;
    public FileStorage(String pathFile, Context context){
        _pathFile = pathFile;
        _context = context;
    }

    public String GetFile(String nameFile, String codeCreditSubject){
        File fileLocal = null;
        String pathFileLocal = _pathFile+"/"+nameFile;
        String base64 = null;
        try {
            fileLocal = new File(pathFileLocal);

            byte[] buffer = new byte[(int) fileLocal.length() + 100];
            int length = new FileInputStream(fileLocal).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (Exception ex) {
            LogError.SendErrorCrashlytics(_context.getClass().getSimpleName(),codeCreditSubject,ex,_context);
            System.out.println("Ha ocurrido un error en la lectura del archivo! "+ex.getMessage());
            ex.printStackTrace();
        }
        return base64;
    }
}
