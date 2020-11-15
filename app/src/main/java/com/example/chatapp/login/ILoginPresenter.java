package com.example.chatapp.login;

public interface ILoginPresenter {
    void handleLogin(String email, String password);
    void onSuccess();
    void onError(int msgId);
}
