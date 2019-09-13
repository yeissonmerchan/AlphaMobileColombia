package com.example.alphamobilecolombia.utils.configuration;

import com.example.alphamobilecolombia.data.local.entity.FileStorage;

import java.util.List;

public interface IFileStorageService {
    boolean InsertFilesForCreditSubject(List<FileStorage> fileStorage);
    boolean DeleteForCreditSubject(int idSubjectCredit);
    List<FileStorage> GetListForCreditSubject(int idSubjectCredit);
    boolean UpdateState(FileStorage fileStorage);
    boolean CleanCreditValidation();
    List<FileStorage> GetListForAll();
}
