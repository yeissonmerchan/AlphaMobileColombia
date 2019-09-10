package com.example.alphamobilecolombia.mvp.presenter.implement;

import com.example.alphamobilecolombia.mvp.presenter.IModulePresenter;
import com.example.alphamobilecolombia.utils.configuration.IParameterField;

public class ModulePresenter implements IModulePresenter {
    IParameterField _iParameterField;
    public ModulePresenter(IParameterField iParameterField){
        _iParameterField = iParameterField;
    }

    public boolean CleanCreditInformation(){
        return _iParameterField.CleanCreditValidation();
    }
}
