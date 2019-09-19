package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.mvp.adapter.IPersonAdapter;
import com.example.alphamobilecolombia.mvp.models.Person;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.example.alphamobilecolombia.mvp.presenter.IPersonPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;

import org.json.JSONArray;
import org.json.JSONObject;

public class PersonPresenter implements IPersonPresenter {
    IPersonAdapter _iPersonAdapter;
    Context _context;
    String messageError;
    int codePerson = 0;
    public PersonPresenter(IPersonAdapter iPersonAdapter, Context context){
        _iPersonAdapter = iPersonAdapter;
        _context = context;
    }

    public boolean SavePerson(Persona person, String idUser){
        boolean result = false;
        try {
            ApiResponse apiResponse = _iPersonAdapter.Post(person, idUser);
            if (apiResponse.getCodigoRespuesta() == 200) {
                String data = apiResponse.getData().toString();
                JSONArray jsonObject = new JSONArray(data);
                JSONObject objeto = (JSONObject) jsonObject.get(0);
                String codigoTransaccion = String.valueOf(apiResponse.getCodigoTransaccion());
                codePerson = Integer.parseInt(codigoTransaccion);
                result = true;
            } else {
                messageError = apiResponse.getMensaje();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Ha ocurrido un error! "+ex.getMessage());
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"SavePerson: " + person.getCedula(),ex,_context);
        }

        return result;
    }

    public String MessageError(){
        return messageError;
    }

    public int GetIdPerson(){
        return codePerson;
    }
}
