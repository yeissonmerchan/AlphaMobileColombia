package com.example.alphamobilecolombia.utils.configuration.implement;

import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.SelectionOption;
import com.example.alphamobilecolombia.utils.configuration.ISelectList;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectList implements ISelectList {

    IRealmInstance _iRealmInstance;
    public SelectList(IRealmInstance iRealmInstance){
        _iRealmInstance = iRealmInstance;
    }

    public int GetValueByCodeField(String nameOption){
        int returnValue = 0;
        try {
            SelectionOption selectionOption = _iRealmInstance.GetByAttribute(new SelectionOption(), "NameOption", nameOption);
            returnValue = selectionOption.getCodeOption();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public int GetValueByIdField(String nameOption){
        int returnValue = 0;
        try {
            SelectionOption selectionOption = _iRealmInstance.GetByAttribute(new SelectionOption(),"NameOption",nameOption);
            returnValue = selectionOption.getCodeOption();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return returnValue;
    }

    public List<SelectionOption> GetListByCodeField(String codeField){
        List<SelectionOption> listReturn = new ArrayList();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"CodeField",codeField);
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(new SelectionOption(selectionOption1.getCodeField(),selectionOption1.getIdField(),selectionOption1.getCodeOption(),selectionOption1.getNameOption()));
        }

        return listReturn;
    }

    public List<SelectionOption> GetListByIdField(String idField){
        List<SelectionOption> listReturn = new ArrayList();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"IdField",idField);
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(new SelectionOption(selectionOption1.getCodeField(),selectionOption1.getIdField(),selectionOption1.getCodeOption(),selectionOption1.getNameOption()));
        }

        return listReturn;
    }

    public List<String> GetArrayByCodeField(String codeField){
        ArrayList<String> listReturn = new ArrayList<String>();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"CodeField",codeField);
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(selectionOption1.getNameOption());
        }
        Object[] objectList = listReturn.toArray();
        String[] stringArray =  Arrays.copyOf(objectList,objectList.length,String[].class);
        List<String> list = Arrays.asList(stringArray);

        return list;
    }

    public List<String> GetArrayByIdField(String idField){
        ArrayList<String> listReturn = new ArrayList<String>();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"IdField",idField);
        List<SelectionOption> selectionOption22 = _iRealmInstance.GetAllGeneric(new SelectionOption());
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(selectionOption1.getNameOption());
        }
        Object[] objectList = listReturn.toArray();
        String[] stringArray =  Arrays.copyOf(objectList,objectList.length,String[].class);
        List<String> list = Arrays.asList(stringArray);

        return list;
    }
}
