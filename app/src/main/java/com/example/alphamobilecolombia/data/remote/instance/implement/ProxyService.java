package com.example.alphamobilecolombia.data.remote.instance.implement;

import com.example.alphamobilecolombia.data.remote.Models.Response.ApiResponse;
import com.example.alphamobilecolombia.data.remote.Models.entity.Token;
import com.example.alphamobilecolombia.data.remote.instance.IProxyService;
import com.example.alphamobilecolombia.mvp.adapter.IProxyServiceAdapter;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.security.IAccessToken;

public class ProxyService implements IProxyService {
    IProxyServiceAdapter _iProxyServiceAdapter;
    IAccessToken _iAccessToken;
    public ProxyService(IProxyServiceAdapter iProxyServiceAdapter, IAccessToken iAccessToken){
        _iProxyServiceAdapter = iProxyServiceAdapter;
        _iAccessToken = iAccessToken;
    }

    public boolean NotifyNewToken(){
        boolean isValid = false;
        try {
            Token token = _iAccessToken.RefreshAccess();
            if (token != null){
                ApiResponse tokenRefresh = _iProxyServiceAdapter.Post(token);
                if (tokenRefresh != null){
                    isValid = _iAccessToken.GrantedAccess(token);
                }
                else{
                    throw new Exception("");
                }
            }
            else{
                throw new Exception("");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return isValid;
    }
}
