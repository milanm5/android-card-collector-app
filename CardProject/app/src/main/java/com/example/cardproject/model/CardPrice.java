package com.example.cardproject.model;

import com.google.gson.annotations.SerializedName;

public class CardPrice {


    @SerializedName("usd")
    private String usd;

    @SerializedName("eur")
    private String eur;

    public CardPrice() {}

    public CardPrice(String usd, String eur) {
        this.usd = usd;
        this.eur = eur;
    }

    public String getUsd() {
        return usd;
    }

    public void setUsd(String usd) {
        this.usd = usd;
    }

    public String getEur() {
        return eur;
    }

    public void setEur(String eur) {
        this.eur = eur;
    }
}
