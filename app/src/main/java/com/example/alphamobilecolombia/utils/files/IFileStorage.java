package com.example.alphamobilecolombia.utils.files;

import android.content.Context;

import com.example.alphamobilecolombia.mvp.models.File;

import java.util.List;

public interface IFileStorage {
    String GetFile(String nameFile, String codeCreditSubject,String pathFile);
    //boolean DeleteFile();
    List<File> GetListFiles();
}
