package com.example.chatapp.registration;

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
import com.example.chatapp.util.FragmentNavigation;
import com.example.chatapp.util.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegistrationFragment extends Fragment implements IRegistrationView {

    private MaterialEditText usernameMaterialEditText, passwordMaterialEditText, emailMaterialEditText;
    private Button registrationButton;
    private IRegistrationPresenter registrationPresenter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.initializeViewElements(view);
        this.registrationButton.setOnClickListener(v -> {
            this.progressBar.setVisibility(View.VISIBLE);
            this.registrationPresenter = new RegistrationPresenter(this);
            this.registrationPresenter.handleRegistration(this.usernameMaterialEditText.getText().toString(),
                    this.emailMaterialEditText.getText().toString(),
                    this.passwordMaterialEditText.getText().toString());
            Utils.hideKeyboard(getActivity());
        });
    }

    private void initializeViewElements(View view) {
        this.emailMaterialEditText = view.findViewById(R.id.email_materialEditText);
        this.passwordMaterialEditText = view.findViewById(R.id.password_materialEditText);
        this.usernameMaterialEditText = view.findViewById(R.id.username_materialEditText);
        this.registrationButton = view.findViewById(R.id.registration_button);
        this.progressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public void informError(int msgId) {
        this.progressBar.setVisibility(View.INVISIBLE);
        Utils.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.purple_200);
    }

    @Override
    public void createUserSuccess() {
        this.progressBar.setVisibility(View.INVISIBLE);
        Utils.makeSnackBar(getView(), R.string.registration_success, Snackbar.LENGTH_SHORT, R.color.teal_700);
        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
    }
}
