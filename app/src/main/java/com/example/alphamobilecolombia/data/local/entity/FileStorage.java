package com.example.alphamobilecolombia.data.local.entity;

import io.realm.RealmObject;

public class FileStorage extends RealmObject {
    private int IdPerson;
    private int IdCreditSubject;
    private int IdTypeFile;
    private int DocumentNumber;
    private String NameType;
    private String Name;
    private boolean IsRequired;

    public boolean isUpload() {
        return IsUpload;
    }

    public void setUpload(boolean upload) {
        IsUpload = upload;
    }

    private boolean IsUpload;
    private String FilePath;

    public int getIdPerson() {
        return IdPerson;
    }

    public void setIdPerson(int idPerson) {
        IdPerson = idPerson;
    }

    public int getIdCreditSubject() {
        return IdCreditSubject;
    }

    public void setIdCreditSubject(int idCreditSubject) {
        IdCreditSubject = idCreditSubject;
    }

    public int getIdTypeFile() {
        return IdTypeFile;
    }

    public void setIdTypeFile(int idTypeFile) {
        IdTypeFile = idTypeFile;
    }

    public int getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(int documentNumber) {
        DocumentNumber = documentNumber;
    }

    public String getNameType() {
        return NameType;
    }

    public void setNameType(String nameType) {
        NameType = nameType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isRequired() {
        return IsRequired;
    }

    public void setRequired(boolean required) {
        IsRequired = required;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }


}
