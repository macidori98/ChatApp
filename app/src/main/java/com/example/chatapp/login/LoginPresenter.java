package com.example.chatapp.login;

import com.example.chatapp.R;
import com.example.chatapp.databaseHelper.FirebaseDb;
import com.example.chatapp.util.GlobalValues;
import com.example.chatapp.util.Utils;

public class LoginPresenter implements ILoginPresenter{

    private final ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void handleLogin(String email, String password) {
        if (!Utils.isValidEmail(email)) {
            this.onError(R.string.invalid_email);
            return;
        }

        if (Utils.isValidStringLength(password, GlobalValues.STRING_MIN_LENGTH)) {
            this.onError(R.string.invalid_username);
            return;
        }

        FirebaseDb.getInstance().login(email, password, this);
    }

    @Override
    public void onSuccess() {
        this.loginView.onSuccess();
    }

    @Override
    public void onError(int msgId) {
        this.loginView.informUser(msgId);
    }
}
