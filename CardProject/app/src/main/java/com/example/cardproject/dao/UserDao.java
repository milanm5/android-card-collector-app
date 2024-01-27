package com.example.cardproject.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cardproject.model.User;

@Dao
public interface UserDao {

    @Insert
    Long insert(User user);

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    User loginUser(String email, String password);

    @Query("SELECT * FROM User WHERE email = :email")
    User getUserByEmail(String email);
}
