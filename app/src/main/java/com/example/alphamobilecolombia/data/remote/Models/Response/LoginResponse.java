package com.example.alphamobilecolombia.data.remote.Models.Response;

import com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation;
import com.example.alphamobilecolombia.data.remote.Models.entity.Token;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {
    private UserRoleInformation[] UserRoleInformation;

    public com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation[] getUserRoleInformation() {
        return UserRoleInformation;
    }

    public void setUserRoleInformation(com.example.alphamobilecolombia.data.remote.Models.entity.UserRoleInformation[] userRoleInformation) {
        UserRoleInformation = userRoleInformation;
    }
}
