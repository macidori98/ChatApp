package com.example.chatapp.registration;

import com.example.chatapp.R;
import com.example.chatapp.databaseHelper.FirebaseDb;
import com.example.chatapp.util.Utils;

public class RegistrationPresenter implements IRegistrationPresenter {
    private final IRegistrationView registrationView;

    public RegistrationPresenter(IRegistrationView registrationView) {
        this.registrationView = registrationView;
    }

    @Override
    public void handleRegistration(String username, String email, String password) {
        if (!Utils.isValidEmail(email)) {
            this.onError(R.string.invalid_email);
            return;
        }

        if (Utils.isValidStringLength(username, 6)) {
            this.onError(R.string.invalid_username);
            return;
        }

        if (Utils.isValidStringLength(password, 6)) {
            this.onError(R.string.invalid_password);
            return;
        }

        FirebaseDb.getInstance().createUser(email, password, username, this);
    }

    @Override
    public void onSuccess() {
        this.registrationView.createUserSuccess();
    }

    @Override
    public void onError(int msgId) {
        this.registrationView.informError(msgId);
    }
}
