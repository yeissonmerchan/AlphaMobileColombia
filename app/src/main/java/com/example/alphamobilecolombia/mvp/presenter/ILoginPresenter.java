package com.example.alphamobilecolombia.mvp.presenter;

public interface ILoginPresenter {
    boolean LoginCheck(String txtUser, String txtPassword);
    String MessageError();
}
