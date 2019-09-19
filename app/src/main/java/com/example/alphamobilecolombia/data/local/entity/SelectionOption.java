package com.example.alphamobilecolombia.data.local.entity;

import io.realm.RealmObject;

public class SelectionOption extends RealmObject {
    private String CodeField;
    private String IdField;
    private int CodeOption;
    private String NameOption;

    public SelectionOption(){}

    public SelectionOption(String CodeField,String IdField,int CodeOption,String NameOption){
        this.CodeField = CodeField;
        this.CodeOption = CodeOption;
        this.IdField = IdField;
        this.NameOption = NameOption;
    }

    public String getCodeField() {
        return CodeField;
    }

    public void setCodeField(String codeField) {
        CodeField = codeField;
    }

    public String getIdField() {
        return IdField;
    }

    public void setIdField(String idField) {
        IdField = idField;
    }

    public int getCodeOption() {
        return CodeOption;
    }

    public void setCodeOption(int codeOption) {
        CodeOption = codeOption;
    }

    public String getNameOption() {
        return NameOption;
    }

    public void setNameOption(String nameOption) {
        NameOption = nameOption;
    }


}
