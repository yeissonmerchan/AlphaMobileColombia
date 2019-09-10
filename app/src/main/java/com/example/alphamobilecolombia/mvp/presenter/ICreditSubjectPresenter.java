package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.mvp.models.Persona;

public interface ICreditSubjectPresenter {
    boolean SaveCreditSubject(Persona person, String idUser, int idPerson, int typeEmployee, int typeContract, int creditDestination, int codePayMaster);
    String MessageError();
    int GetIdSubjectCredit();
}
