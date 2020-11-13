package com.example.chatapp.databaseHelper;

import androidx.annotation.NonNull;

import com.example.chatapp.R;
import com.example.chatapp.registration.IRegistrationPresenter;
import com.example.chatapp.util.GlobalValues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class FirebaseDb {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference;
    private static FirebaseUser firebaseUser;
    private static FirebaseDb databaseInstance;

    private FirebaseDb() {
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseDb getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new FirebaseDb();
        }

        return databaseInstance;
    }

    public void createUser(String email, String password, String username, IRegistrationPresenter registrationPresenter) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                GlobalValues.CURRENT_USER = firebaseUser;
                insertUser(username, registrationPresenter);
            } else {
                //failure
                registrationPresenter.onError(R.string.user_create_fail);
            }
        });
    }

    private void insertUser(String username, IRegistrationPresenter registrationPresenter) {
        String userID = Objects.requireNonNull(firebaseUser).getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference(GlobalValues.USERS).child(userID);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(GlobalValues.ID, userID);
        hashMap.put(GlobalValues.USERNAME, username);
        hashMap.put(GlobalValues.IMAGE_URL, GlobalValues.DEFAULT);

        databaseReference.setValue(hashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //go to homepage
                registrationPresenter.onSuccess();
            } else {
                //failure
                registrationPresenter.onError(R.string.user_create_fail);
            }
        });
    }

}
