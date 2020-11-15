package com.example.chatapp.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chatapp.R;
import com.example.chatapp.home.HomeFragment;
import com.example.chatapp.registration.RegistrationFragment;
import com.example.chatapp.util.FragmentNavigation;
import com.example.chatapp.util.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginFragment extends Fragment implements ILoginView{

    private MaterialEditText passwordMaterialEditText, emailMaterialEditText;
    private Button registrationButton, loginButton;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initializeViewElements(view);
        this.setButtonOnClickListeners();
    }

    private void initializeViewElements(View view) {
        this.emailMaterialEditText = view.findViewById(R.id.email_materialEditText);
        this.passwordMaterialEditText = view.findViewById(R.id.password_materialEditText);
        this.registrationButton = view.findViewById(R.id.registration_button);
        this.loginButton = view.findViewById(R.id.login_button);
        this.progressBar = view.findViewById(R.id.progressBar);
    }

    private void setButtonOnClickListeners() {
        this.loginButton.setOnClickListener(v -> {
            ILoginPresenter loginPresenter = new LoginPresenter(this);
            loginPresenter.handleLogin(this.emailMaterialEditText.getText().toString(),
                    this.passwordMaterialEditText.getText().toString());
        });

        this.registrationButton.setOnClickListener(
                v -> FragmentNavigation.getInstance(getContext())
                        .replaceFragment(new RegistrationFragment(), R.id.fragment_content));
    }

    @Override
    public void informUser(int msgId) {
        this.progressBar.setVisibility(View.INVISIBLE);
        Utils.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.purple_200);
    }

    @Override
    public void onSuccess() {
        this.progressBar.setVisibility(View.INVISIBLE);
        Utils.makeSnackBar(getView(), R.string.registration_success, Snackbar.LENGTH_SHORT, R.color.teal_700);
        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
    }
}
