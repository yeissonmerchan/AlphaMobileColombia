package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import org.json.JSONObject;

public class CreditSubjectPresenter implements ICreditSubjectPresenter {
    ICreditSubjectAdapter _iCreditSubjectAdapter;
    Context _context;
    String messageError;
    int codeSubjectCredit = 0;

    public CreditSubjectPresenter(ICreditSubjectAdapter iCreditSubjectAdapter, Context context){
        _iCreditSubjectAdapter = iCreditSubjectAdapter;
        _context = context;
    }

    public boolean SaveCreditSubject(Persona person, String idUser, int idPerson){
        boolean result = false;
        try {
            ApiResponse apiResponse = _iCreditSubjectAdapter.Post(person, String.valueOf(idPerson),0,0,0,idUser,0);
            if (apiResponse.getCodigoRespuesta() == 200) {
                JSONObject objectData = (JSONObject) apiResponse.getData();
                String codeTransaction = objectData.getString("codigoTransaccion");
                codeSubjectCredit = Integer.parseInt(codeTransaction);
                result = true;
            } else {
                messageError = apiResponse.getMensaje();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"Guardar persona " +person.getCedula(),ex,_context);
        }
        return result;
    }


    public String MessageError(){
        return messageError;
    }

    public int GetIdSubjectCredit(){
        return codeSubjectCredit;
    }
}
