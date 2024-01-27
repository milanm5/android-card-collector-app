package com.example.cardproject.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cardproject.model.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    void addCardToCart(Cart cart);

    @Query("SELECT * FROM Cart WHERE userId = :loggedUser AND cardId = :cardId")
    Cart isCardInCart(int loggedUser, String cardId);

    @Query("SELECT * FROM Cart WHERE userId = :loggedUser")
    LiveData<List<Cart>> getAllCardsInCart(int loggedUser);

    @Update
    void updateCart(Cart... carts);

    @Query("DELETE FROM Cart")
    void order();
}
