package com.example.chatapp.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void makeSnackBar(View view, int textId, int length, int backgroundColorId) {
        Snackbar snackbar = Snackbar.make(view, textId, length);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), backgroundColorId));
        snackbar.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidStringLength(String target, int length) {
        return (TextUtils.isEmpty(target) || (target.length() < length));
    }
}
