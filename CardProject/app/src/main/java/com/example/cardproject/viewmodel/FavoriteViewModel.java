package com.example.cardproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardproject.dao.FavoriteCardDao;
import com.example.cardproject.data.CardDatabase;
import com.example.cardproject.model.FavoriteCard;
import com.example.cardproject.util.Constants;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteCardDao favoriteCardDao;

    public LiveData<List<FavoriteCard>> favoriteCardsLiveData;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        favoriteCardDao = CardDatabase.getInstance(application.getApplicationContext()).favoriteCardDao();
        favoriteCardsLiveData = favoriteCardDao.getFavoriteCardsLiveData(Constants.LOGGED_IN_USER);
    }
}
