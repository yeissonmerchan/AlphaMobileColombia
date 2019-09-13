package com.example.alphamobilecolombia.utils.configuration.implement;

import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.FileStorage;
import com.example.alphamobilecolombia.data.local.entity.Parameter;
import com.example.alphamobilecolombia.data.local.entity.SelectionOption;
import com.example.alphamobilecolombia.utils.configuration.IFileStorageService;

import java.util.ArrayList;
import java.util.List;

public class FileStorageService implements IFileStorageService {
    IRealmInstance _iRealmInstance;
    public FileStorageService(IRealmInstance iRealmInstance){
        _iRealmInstance = iRealmInstance;
    }

    public boolean InsertFilesForCreditSubject(List<FileStorage> fileStorage){
        boolean isValid = false;
        try {
            isValid = _iRealmInstance.Insert(fileStorage);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return isValid;
    }

    public List<FileStorage> GetListForAll(){
        List<FileStorage> returnValue = new ArrayList<>();
        try {
            returnValue = _iRealmInstance.GetAll(new FileStorage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public boolean DeleteForCreditSubject(int idSubjectCredit){
        boolean returnValue = false;
        try {
            returnValue = _iRealmInstance.DeleteByKey(new FileStorage(),"IdCreditSubject", idSubjectCredit);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public List<FileStorage> GetListForCreditSubject(int idSubjectCredit){
        List<FileStorage> returnValue = new ArrayList<>();
        try {
            returnValue = _iRealmInstance.GetAllByAttribute(new FileStorage(),"IdCreditSubject", idSubjectCredit);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public boolean UpdateState(FileStorage fileStorage){
        boolean returnValue = false;
        try {
            returnValue = _iRealmInstance.Update(fileStorage);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public boolean CleanCreditValidation(){
        boolean returnValue = false;
        try {
            returnValue = _iRealmInstance.DeleteObject(new Parameter());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }
}
