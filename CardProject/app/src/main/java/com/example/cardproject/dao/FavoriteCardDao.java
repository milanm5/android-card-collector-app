package com.example.cardproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cardproject.model.FavoriteCard;

import java.util.List;

@Dao
public interface FavoriteCardDao {

    @Insert
    void insert(FavoriteCard favoriteCard);

    @Delete
    void delete(FavoriteCard favoriteCard);

    @Query("SELECT * FROM FavoriteCard WHERE cardId = :cardId AND userId = :loggedUser")
    FavoriteCard isFavCardAdded(String cardId, int loggedUser);

    @Query("SELECT * FROM FavoriteCard WHERE cardId = :cardId AND userId = :loggedUser")
    LiveData<FavoriteCard> isCardAddedToFavLiveData(String cardId, int loggedUser);

    @Query("SELECT * FROM FavoriteCard WHERE userId = :loggedUser")
    LiveData<List<FavoriteCard>> getFavoriteCardsLiveData(int loggedUser);
}
