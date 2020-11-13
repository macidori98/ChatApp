package com.example.chatapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.registration.RegistrationFragment;
import com.example.chatapp.util.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance(this).replaceFragment(new RegistrationFragment(), R.id.fragment_content);
    }

    @Override
    public void onBackPressed() {
        FragmentNavigation.getInstance(this).onBackPressed(this);
    }
}