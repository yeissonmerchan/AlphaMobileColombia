package com.example.alphamobilecolombia.mvp.presenter.implement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alphamobilecolombia.data.cloud.firestore.ICloudStoreInstance;
import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.remote.Models.Request.PostUserRequest;
import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation;
import com.example.alphamobilecolombia.mvp.adapter.ILoginAdapter;
import com.example.alphamobilecolombia.mvp.presenter.ILoginPresenter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.cryptography.providers.IMD5Hashing;
import com.example.alphamobilecolombia.utils.security.IAccessToken;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements ILoginPresenter {
    private Context _context;
    private ILoginAdapter _iLoginAdapter;
    private IMD5Hashing _imd5Hashing;
    private String messageError;
    private ICloudStoreInstance _iCloudStoreInstance;
    private IRealmInstance _iRealmInstance;
    private IAccessToken _iAccessToken;

    public LoginPresenter(Context context, ILoginAdapter iloginAdapter, IMD5Hashing imd5Hashing, ICloudStoreInstance iCloudStoreInstance, IRealmInstance iRealmInstance, IAccessToken iAccessToken){
        _iLoginAdapter = iloginAdapter;
        _imd5Hashing = imd5Hashing;
        _context = context;
        _iCloudStoreInstance = iCloudStoreInstance;
        _iRealmInstance = iRealmInstance;
        _iAccessToken = iAccessToken;
    }

    public boolean LoginCheck(String txtUser, String txtPassword){
        boolean result = false;
        ApiResponse apiResponse = _iLoginAdapter.Post(txtUser,_imd5Hashing.Encrypt(txtPassword));
        if(apiResponse.getCodigoRespuesta().toString().contains("200")){
            result = true;
            try {
                String data = apiResponse.getData().toString();
                JSONArray jsonObject = new JSONArray(data);
                UserRoleInformation[] httpResponse = new Gson().fromJson(jsonObject.toString(), UserRoleInformation[].class);
                PostUserRequest postUserRequest = new PostUserRequest();

                result = _iAccessToken.GrantedAccess(apiResponse.getToken());

                SharedPreferences sharedPref = _context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                postUserRequest.setData(sharedPref, (JSONObject) jsonObject.get(0), txtUser);

            }
            catch (JSONException e) {
                e.printStackTrace();
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"LoginCheck: " + txtUser,e,_context);
            } catch (Exception e) {
                e.printStackTrace();
                LogError.SendErrorCrashlytics(this.getClass().getSimpleName(),"LoginCheck: " + txtUser,e,_context);
            }
        }
        messageError = apiResponse.getMensaje();
        return result;
    }


    public String MessageError(){
        return messageError;
    }
}
