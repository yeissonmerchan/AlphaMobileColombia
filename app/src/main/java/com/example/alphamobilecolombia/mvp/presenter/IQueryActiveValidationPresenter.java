package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.data.remote.Models.Response.ActiveValidationResponse;

public interface IQueryActiveValidationPresenter {
    ActiveValidationResponse IsActiveValidation(String documentNumber);
}
