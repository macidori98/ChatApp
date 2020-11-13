package com.example.chatapp.registration;

public interface IRegistrationPresenter {
    void handleRegistration(String username, String email, String password);

    void onSuccess();

    void onError(int msgId);
}
