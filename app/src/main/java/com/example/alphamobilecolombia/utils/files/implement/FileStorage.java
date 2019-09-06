package com.example.alphamobilecolombia.utils.files.implement;

import android.content.Context;
import android.util.Base64;

import com.example.alphamobilecolombia.utils.configuration.implement.ApplicationData;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.files.IFileStorage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements IFileStorage {
    Context _context;
    public FileStorage(Context context){
        _context = context;
    }

    public String GetFile(String nameFile, String codeCreditSubject,String pathFile){
        File fileLocal = null;
        String pathFileLocal = pathFile+"/"+nameFile;
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

    public boolean DeleteFile(){
        ApplicationData applicationData = new ApplicationData();
        applicationData.ClearDirectoryTemp(_context);
        return true;
    }

    public List<com.example.alphamobilecolombia.mvp.models.File> GetListFiles(){
        List<com.example.alphamobilecolombia.mvp.models.File> filesRequired = new ArrayList<>();
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(27,"",true,"CargueDocumentosPreValidación",false,"", true));
        //filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(67,"",true,"SolicitudCreditoCara2",false,"", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(68,"",true,"CedulaCara1",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(69,"",true,"CedulaCara2",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(70,"",true,"Desprendible1",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(71,"",true,"Desprendible2",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(72,"",false,"Desprendible3",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(73,"",false,"Desprendible4",false, "", true));
        filesRequired.add(new com.example.alphamobilecolombia.mvp.models.File(77,"",true,"TratamientoDatosPersonales",false, "", true));

        return filesRequired;
    }
}
