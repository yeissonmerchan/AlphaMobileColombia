package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.adapter.ICreditSubjectAdapter;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.mvp.presenter.ICreditSubjectPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import org.json.JSONArray;
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

    public boolean SaveCreditSubject(Persona person, String idUser, int idPerson, int typeEmployee, int typeContract, int creditDestination, int codePayMaster){
        boolean result = false;
        try {
            ApiResponse apiResponse = _iCreditSubjectAdapter.Post(person, String.valueOf(idPerson),typeEmployee,typeContract,creditDestination,idUser,codePayMaster);
            if (apiResponse.getCodigoRespuesta() == 200) {
                String data = apiResponse.getData().toString();
                JSONArray jsonObject = new JSONArray(data);
                JSONObject objeto = (JSONObject) jsonObject.get(0);
                String codeTransaction = String.valueOf(apiResponse.getCodigoTransaccion());
                codeSubjectCredit = Integer.parseInt(codeTransaction);
                result = true;
            } else {
                messageError = apiResponse.getMensaje();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"SaveCreditSubject" +person.getCedula(),ex,_context);
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
