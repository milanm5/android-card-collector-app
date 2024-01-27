package com.example.cardproject.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cardproject.dao.CartDao;
import com.example.cardproject.data.CardDatabase;
import com.example.cardproject.model.Cart;
import com.example.cardproject.util.Constants;
import com.example.cardproject.util.OperationResult;

import java.util.List;

public class CartViewModel extends AndroidViewModel {

    private final CartDao cartDao;


    public final LiveData<List<Cart>> cartListLiveData;

    public CartViewModel(@NonNull Application application) {
        super(application);

        cartDao = CardDatabase.getInstance(application.getApplicationContext()).cartDao();
        cartListLiveData = cartDao.getAllCardsInCart(Constants.LOGGED_IN_USER);
    }

    public void order() {
        new OrderAsync().execute();
    }

    private class OrderAsync extends AsyncTask<String, Integer, OperationResult> {

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                cartDao.order();
                return OperationResult.success("Order successful!", null);
            } catch (Exception e) {
                return OperationResult.error("There was an error. Please try again later!");
            }
        }
    }
}
