package com.example.cardproject.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardproject.dao.CartDao;
import com.example.cardproject.dao.FavoriteCardDao;
import com.example.cardproject.dao.UserDao;
import com.example.cardproject.model.Cart;
import com.example.cardproject.model.FavoriteCard;
import com.example.cardproject.model.User;

@Database(
        entities = {User.class, FavoriteCard.class, Cart.class},
        version = 1,
        exportSchema = false
)
public abstract class CardDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract FavoriteCardDao favoriteCardDao();

    public abstract CartDao cartDao();

    private static CardDatabase INSTANCE;

    public static CardDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CardDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    CardDatabase.class,
                                    "card_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
