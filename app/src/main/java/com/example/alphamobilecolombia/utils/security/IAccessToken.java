package com.example.alphamobilecolombia.utils.security;

import com.example.alphamobilecolombia.data.remote.Models.entity.Token;

public interface IAccessToken {
    boolean GrantedAccess(Token token);
    Token RefreshAccess();
    //boolean CleanToken();
}
