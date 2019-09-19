package com.example.alphamobilecolombia.mvp.presenter;

import com.example.alphamobilecolombia.mvp.models.Persona;

public interface IPersonPresenter {
    boolean SavePerson(Persona person, String idUser);
    String MessageError();
    int GetIdPerson();
}
