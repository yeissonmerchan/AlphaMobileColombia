package com.example.alphamobilecolombia.utils.security.implement;

import com.example.alphamobilecolombia.data.local.IRealmInstance;
import com.example.alphamobilecolombia.data.remote.Models.entity.Token;
import com.example.alphamobilecolombia.utils.crashlytics.LogError;
import com.example.alphamobilecolombia.utils.security.IAccessToken;

import java.util.List;

public class AccessToken implements IAccessToken {
    private IRealmInstance _iRealmInstance;

    public AccessToken(IRealmInstance iRealmInstance) {
        _iRealmInstance = iRealmInstance;
    }

    public boolean GrantedAccess(Token token) {
        boolean isValid;
        try {
            if (token != null) {
                _iRealmInstance.DeleteObject(new Token());
                _iRealmInstance.Insert(token);
                isValid = true;
            } else {
                throw new Exception("El token no es valido!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            isValid = false;
        }
        return isValid;
    }

    public Token RefreshAccess(){
        Token isValid;
        try {
            List<Token> tokenList = _iRealmInstance.GetAllGeneric(new Token());
            if (tokenList.size() > 0) {
                Token token = tokenList.get(0);
                if (token != null) {
                    isValid = token;
                } else {
                    throw new Exception("El token no es válido!");
                }
            }
            else{
                throw new Exception("El token no es válido!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            isValid = null;
        }
        return isValid;
    }

    public boolean CleanToken(){
        boolean isValid;
        try {
            isValid = _iRealmInstance.DeleteObject(new Token());
        } catch (Exception e) {
            e.printStackTrace();
            isValid = false;
        }
        return isValid;
    }
}
