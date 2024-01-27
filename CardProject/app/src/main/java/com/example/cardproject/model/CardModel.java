package com.example.cardproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CardModel implements Serializable {
    @SerializedName("id")
    private String cardId;
    @SerializedName("name")
    private String cardName;
    @SerializedName("image_uris")
    private CardImage images;
    @SerializedName("type_line")
    private String type;
    @SerializedName("prices")
    private CardPrice prices;
    @SerializedName("set_name")
    private String set;
    @SerializedName("rarity")
    private String rarity;
    @SerializedName("artist")
    private String artist;

    public CardModel(String cardId,String cardName, CardImage images, String type, CardPrice prices, String set, String rarity, String artist) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.images = images;
        this.type = type;
        this.prices = prices;
        this.set = set;
        this.rarity = rarity;
        this.artist = artist;
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

    public CardImage getImages() {
        return images;
    }

    public void setImages(CardImage images) {
        this.images = images;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CardPrice getPrices() {
        return prices;
    }

    public void setPrices(CardPrice prices) {
        this.prices = prices;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
