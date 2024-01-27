package com.example.cardproject.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cardproject.dao.UserDao;
import com.example.cardproject.data.CardDatabase;
import com.example.cardproject.model.User;
import com.example.cardproject.util.Constants;
import com.example.cardproject.util.OperationResult;

public class LoginViewModel extends AndroidViewModel {

    private final UserDao userDao;

    private final MutableLiveData<OperationResult> _loginUserResult;

    public LiveData<OperationResult> loginUserResult;


    public LoginViewModel(@NonNull Application application) {
        super(application);

        userDao = CardDatabase.getInstance(application.getApplicationContext()).userDao();
        _loginUserResult = new MutableLiveData<>();
        loginUserResult = _loginUserResult;
    }

    public void loginUser(String email, String password) {
        new LoginUserAsync(email, password).execute();
    }

    private class LoginUserAsync extends AsyncTask<String, Integer, OperationResult> {

        private final String email;

        private final String password;

        public LoginUserAsync(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                User user = userDao.loginUser(email, password);
                if (user == null) {
                    return OperationResult.error("Wrong email or password");
                } else {
                    Constants.LOGGED_IN_USER = user.getId();
                    return OperationResult.success("Successfully logged in!", null);
                }

            } catch (Exception e) {
                return OperationResult.error(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _loginUserResult.setValue(operationResult);
        }
    }
}