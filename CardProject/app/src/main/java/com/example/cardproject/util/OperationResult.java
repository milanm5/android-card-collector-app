package com.example.cardproject.util;

import androidx.annotation.Nullable;

import com.example.cardproject.model.CardModel;

import java.util.List;

public class OperationResult {

    public final Status status;

    @Nullable
    public String message;
    @Nullable
    public List<CardModel> data;
    @Nullable
    public CardModel cardModel;

    public OperationResult(Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public OperationResult(Status status, @Nullable String message, @Nullable List<CardModel> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static OperationResult error(@Nullable String error) {
        return new OperationResult(Status.ERROR, error);
    }

    public static OperationResult success(@Nullable String message, @Nullable List<CardModel> data) {
        return new OperationResult(Status.SUCCESS, message, data);
    }



}
