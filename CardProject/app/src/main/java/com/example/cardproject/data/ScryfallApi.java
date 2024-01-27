package com.example.cardproject.data;

import com.example.cardproject.model.CardModel;
import com.example.cardproject.model.CardResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScryfallApi {

    @GET("/cards/random")
    Call<CardModel> getRandomCard();

    @GET("/cards/search")
    Call<CardResponse> searchCard(@Query("q") String query);
}
