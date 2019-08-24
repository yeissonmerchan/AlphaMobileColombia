package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.configuration.environment.ApiEnviroment;
import com.example.alphamobilecolombia.data.local.RealmStorage;
import com.example.alphamobilecolombia.data.remote.EndPoint.GetVersion;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostUserRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.Response.HttpResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation;
import com.example.alphamobilecolombia.mvp.adapter.IVersionUpdateAdapter;
import com.example.alphamobilecolombia.mvp.presenter.IVersionUpdatePresenter;
import com.example.alphamobilecolombia.utils.configuration.ApplicationData;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class VersionUpdatePresenter implements IVersionUpdatePresenter {
    private Context _context;
    private FirebaseAnalytics mFireBaseAnalytics;
    private IVersionUpdateAdapter _iVersionUpdateAdapter;
    private String messageError;
    private String version;
    public VersionUpdatePresenter(Context context, IVersionUpdateAdapter iVersionUpdateAdapter){
        _context = context;
        _iVersionUpdateAdapter = iVersionUpdateAdapter;
    }

    public Boolean IsValidVersion(){
        boolean result = false;
        try {
            PackageInfo pInfo = _context.getPackageManager().getPackageInfo(_context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),version,ex,_context);
        }

        ApiResponse apiResponse = _iVersionUpdateAdapter.Get(version);
        try {
            if (apiResponse.getCodigoRespuesta().toString().contains("200")) {
                result = true;

                RealmStorage storage = new RealmStorage();
                storage.initLocalStorage(_context.getApplicationContext());
                mFireBaseAnalytics = FirebaseAnalytics.getInstance(_context.getApplicationContext());
            } else {
                messageError = "Hemos detectado una versión más reciente de la aplicación, por favor debes actualizarla.";
                ApplicationData applicationData = new ApplicationData();
                applicationData.RestartNewVersionApp(_context.getApplicationContext());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),version,e,_context);
        }
        return result;
    }

    public String MessageError(){
        return messageError;

    }
}
