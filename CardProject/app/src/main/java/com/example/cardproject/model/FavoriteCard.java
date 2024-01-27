package com.example.cardproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FavoriteCard implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private String cardId;

    private String cardName;

    private String price;

    private String image;

    private String set;

    private String rarity;

    private String type;
    private String artist;

    public FavoriteCard(int userId, String cardId, String cardName, String price, String image, String set, String rarity, String type, String artist) {
        this.userId = userId;
        this.cardId = cardId;
        this.cardName = cardName;
        this.price = price;
        this.image = image;
        this.set = set;
        this.rarity = rarity;
        this.type = type;
        this.artist = artist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
