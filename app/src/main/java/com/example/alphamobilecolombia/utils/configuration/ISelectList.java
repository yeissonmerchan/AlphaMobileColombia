package com.example.alphamobilecolombia.utils.configuration;

import com.example.alphamobilecolombia.data.local.entity.SelectionOption;

import java.util.ArrayList;
import java.util.List;

public interface ISelectList {
    int GetValueByCodeField(String nameOption);
    int GetValueByIdField(String nameOption);
    List<SelectionOption> GetListByCodeField(String codeField);
    List<SelectionOption> GetListByIdField(String idField);
    ArrayList<String> GetArrayByCodeField(String codeField);
    ArrayList<String> GetArrayByIdField(String idField);
}
