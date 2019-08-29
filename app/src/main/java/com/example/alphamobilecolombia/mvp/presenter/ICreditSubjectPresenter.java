package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.mvp.models.Persona;

public interface ICreditSubjectPresenter {
    boolean SaveCreditSubject(Persona person, String idUser, int idPerson);
    String MessageError();
    int GetIdSubjectCredit();
}
