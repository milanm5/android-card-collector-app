package com.example.cardproject.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardResponse {

    @SerializedName("data")
    List<CardModel> cards;

    public List<CardModel> getCards() {
        return cards;
    }

    public void setCards(List<CardModel> cards) {
        this.cards = cards;
    }
}
