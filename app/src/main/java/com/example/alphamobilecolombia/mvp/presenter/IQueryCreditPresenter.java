package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;

public interface IQueryCreditPresenter {
    PostQueryCredit[] GetQuery(String typeQuery, String pValor, String pRol, String idUser, String initDate, String endDate);
}
