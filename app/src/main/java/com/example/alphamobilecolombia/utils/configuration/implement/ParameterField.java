package com.example.alphamobilecolombia.utils.configuration.implement;

import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.local.entity.Parameter;
import com.example.alphamobilecolombia.data.local.entity.SelectionOption;
import com.example.alphamobilecolombia.utils.configuration.IParameterField;

public class ParameterField implements IParameterField {
    IRealmInstance _iRealmInstance;
    public ParameterField(IRealmInstance iRealmInstance){
        _iRealmInstance = iRealmInstance;
    }

    public String GetValueByCodeField(String codeField){
        String returnValue = null;
        try {
            SelectionOption selectionOption = _iRealmInstance.GetByAttribute(new SelectionOption(), "CodeField", codeField);
            if (selectionOption != null)
            {
                Parameter parameter = _iRealmInstance.GetByAttribute(new Parameter(),"Key",selectionOption.getIdField());
                if(parameter != null){
                    returnValue = parameter.getValue();
                }
            }
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
