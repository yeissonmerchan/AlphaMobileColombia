package com.example.alphamobilecolombia.utils.configuration.implement;

import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.SelectionOption;
import com.example.alphamobilecolombia.utils.configuration.ISelectList;

import java.util.ArrayList;
import java.util.List;

public class SelectList implements ISelectList {

    IRealmInstance _iRealmInstance;
    public SelectList(IRealmInstance iRealmInstance){
        _iRealmInstance = iRealmInstance;
    }

    public int GetValueByCodeField(String nameOption){
        SelectionOption selectionOption = _iRealmInstance.GetByAttribute(new SelectionOption(),"NameOption",nameOption);
        return selectionOption.getCodeOption();
    }

    public int GetValueByIdField(String nameOption){
        SelectionOption selectionOption = _iRealmInstance.GetByAttribute(new SelectionOption(),"NameOption",nameOption);
        return selectionOption.getCodeOption();
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

    public ArrayList<String> GetArrayByCodeField(String codeField){
        ArrayList<String> listReturn = new ArrayList<String>();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"CodeField",codeField);
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(selectionOption1.getNameOption());
        }

        return listReturn;
    }

    public ArrayList<String> GetArrayByIdField(String idField){
        ArrayList<String> listReturn = new ArrayList<String>();
        List<SelectionOption> selectionOption = _iRealmInstance.GetAllByAttribute(new SelectionOption(),"IdField",idField);
        for (SelectionOption selectionOption1 : selectionOption){
            listReturn.add(selectionOption1.getNameOption());
        }

        return listReturn;
    }
}
