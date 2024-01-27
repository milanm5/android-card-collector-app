package com.example.cardproject.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cardproject.data.ScryfallApi;
import com.example.cardproject.model.CardModel;
import com.example.cardproject.model.CardResponse;
import com.example.cardproject.util.OperationResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardViewModel extends AndroidViewModel {

    private final ScryfallApi scryfallApi;

    private final MutableLiveData<OperationResult> _cardResult;

    private final MutableLiveData<OperationResult> _randomCardResult;
    public final LiveData<OperationResult> cardResult;

    public final LiveData<OperationResult> randomCardResult;

    public CardViewModel(@NonNull Application application) {
        super(application);

        _cardResult = new MutableLiveData<>();
        _randomCardResult = new MutableLiveData<>();

        cardResult = _cardResult;
        randomCardResult = _randomCardResult;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.scryfall.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        scryfallApi = retrofit.create(ScryfallApi.class);

    }

    public void insertData() {
        new InsertDataAsync().execute();
    }

    public void search(String query) {
        if (query.isEmpty()) {
            new InsertDataAsync().execute();
        }
        new SearchCardAsync(query).execute();
    }

    public void getRandomCard() {
        new RandomCardAsync().execute();
    }

    private class InsertDataAsync extends AsyncTask<String, Integer, OperationResult> {

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                List<CardModel> cards = new ArrayList<>();

                Call<CardResponse> call = scryfallApi.searchCard("set:gtc");
                Response<CardResponse> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    return OperationResult.success(null, response.body().getCards());
                } else {
                    return OperationResult.error("There was an error. Try again later!");
                }

//                for (int i = 0; i < 20; i++) {
//                    Call<CardModel> call = scryfallApi.getRandomCard();
//                    Response<CardModel> serverResponse = call.execute();
//                    cards.add(serverResponse.body());
//                }

            } catch (Exception e) {
                return OperationResult.error("There was an error. Try again later.");
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _cardResult.setValue(operationResult);
        }
    }

    private class SearchCardAsync extends AsyncTask<String, Integer, OperationResult> {

        private final String query;

        public SearchCardAsync(String query) {
            this.query = query;
        }

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                Call<CardResponse> call = scryfallApi.searchCard(query);
                Response<CardResponse> response = call.execute();

                if (response.isSuccessful() && response.body() != null) {
                    return OperationResult.success(null, response.body().getCards());
                } else {
                    return OperationResult.error("There was an error. Try again later!");
                }
            } catch (Exception e) {
                System.out.println("uhvatio exception");
                return OperationResult.error(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _cardResult.setValue(operationResult);
        }
    }

    private class RandomCardAsync extends AsyncTask<String, Integer, OperationResult> {


        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                Call<CardModel> call = scryfallApi.getRandomCard();
                Response<CardModel> response = call.execute();
                List<CardModel> card = new ArrayList<>();

                if (response.isSuccessful() && response.body() != null) {
                    card.add(response.body());
                    return OperationResult.success(null, card);
                } else {
                    return OperationResult.error("There was an error. Please try again later!");
                }
            } catch (Exception e) {
                return OperationResult.error(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _randomCardResult.setValue(operationResult);
        }
    }
}
