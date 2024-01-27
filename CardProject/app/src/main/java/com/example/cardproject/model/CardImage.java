package com.example.cardproject.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class CardImage {


    @SerializedName("normal")
    private String normal;

    @SerializedName("art_crop")
    @Nullable
    private String artCrop;

    @SerializedName("border_crop")
    private String borderCrop;

    @SerializedName("small")
    private String small;

    public CardImage(){}
    public CardImage(String normal, String artCrop, String borderCrop, String small) {
        this.normal = normal;
        this.artCrop = artCrop;
        this.borderCrop = borderCrop;
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getArtCrop() {
        return artCrop;
    }

    public void setArtCrop(String artCrop) {
        this.artCrop = artCrop;
    }

    public String getBorderCrop() {
        return borderCrop;
    }

    public void setBorderCrop(String borderCrop) {
        this.borderCrop = borderCrop;
    }

    public String getSmall() {
        return this.small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
