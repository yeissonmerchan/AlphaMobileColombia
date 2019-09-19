package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostPersonaInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostSujetoInsertarRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostSujetoCredito;
import com.example.alphamobilecolombia.data.remote.EndPoint.PostPersonaInsertar;
import com.example.alphamobilecolombia.mvp.presenter.IProcessCompletedPresenter;
import com.example.alphamobilecolombia.utils.configuration.IParameterField;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.mvp.models.Persona;
import com.google.gson.Gson;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProcessCompletedPresenter implements IProcessCompletedPresenter {
    IParameterField _iParameterField;

    public ProcessCompletedPresenter(IParameterField iParameterField) {
        _iParameterField = iParameterField;
    }

    public boolean CleanCreditInformation() {
        return _iParameterField.CleanCreditValidation();
    }
}
