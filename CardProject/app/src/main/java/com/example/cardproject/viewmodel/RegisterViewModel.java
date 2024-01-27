package com.example.cardproject.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cardproject.dao.UserDao;
import com.example.cardproject.data.CardDatabase;
import com.example.cardproject.model.User;
import com.example.cardproject.util.OperationResult;

public class RegisterViewModel extends AndroidViewModel {

    private final UserDao userDao;

    private final MutableLiveData<OperationResult> _registerUserResult;

    public LiveData<OperationResult> registerUserResult;

    public RegisterViewModel(@NonNull Application application) {
        super(application);

        _registerUserResult = new MutableLiveData<>();

        registerUserResult = _registerUserResult;

        userDao = CardDatabase.getInstance(application.getApplicationContext()).userDao();
    }

    public void registerUser(String name, String lastName, String address, String email, String password) {
        new RegisterUserAsync(name, lastName, address, email, password).execute();
    }

    private class RegisterUserAsync extends AsyncTask<String, Integer, OperationResult> {

        private final String name;

        private final String lastName;

        private final String address;

        private final String email;

        private final String password;

        public RegisterUserAsync(String name, String lastName, String address, String email, String password) {
            this.name = name;
            this.lastName = lastName;
            this.email = email;
            this.address = address;
            this.password = password;
        }

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                User userToCheck = userDao.getUserByEmail(this.email);
                if (userToCheck != null) {
                    return OperationResult.error("User already exists!");
                }

                User user = new User(name, lastName, address, email, password);

                Long idValue = userDao.insert(user);

                if (idValue > 0) {
                    return OperationResult.success("Registration is successful!", null);
                } else {
                    return OperationResult.error("There was an error with registration. Try again later!");
                }

            } catch (Exception e) {
                return OperationResult.error(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _registerUserResult.setValue(operationResult);
        }
    }
}