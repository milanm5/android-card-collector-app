package com.example.cardproject.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cardproject.dao.CartDao;
import com.example.cardproject.dao.FavoriteCardDao;
import com.example.cardproject.data.CardDatabase;
import com.example.cardproject.model.CardModel;
import com.example.cardproject.model.Cart;
import com.example.cardproject.model.FavoriteCard;
import com.example.cardproject.util.Constants;
import com.example.cardproject.util.OperationResult;

public class CardDetailsViewModel extends AndroidViewModel {

    private final FavoriteCardDao favoriteCardDao;

    private final CartDao cartDao;

    private final MutableLiveData<OperationResult> _addRemoveFavoriteResult;

    public LiveData<OperationResult> addRemoveFavoriteResult;

    public CardDetailsViewModel(@NonNull Application application) {
        super(application);

        favoriteCardDao = CardDatabase.getInstance(application.getApplicationContext()).favoriteCardDao();
        cartDao = CardDatabase.getInstance(application.getApplicationContext()).cartDao();

        _addRemoveFavoriteResult = new MutableLiveData<>();
        addRemoveFavoriteResult = _addRemoveFavoriteResult;
    }

    public LiveData<FavoriteCard> isResAddedToFavLiveData(String cardId) {
        return favoriteCardDao.isCardAddedToFavLiveData(cardId, Constants.LOGGED_IN_USER);
    }

    public void addRemoveFavorites(CardModel cardModel) {
        new AddRemoveFromFavoritesAsync(cardModel).execute();
    }

    public void addToCart(CardModel card, int quantity) {
        new AddToCartAsync(card, quantity).execute();
    }

    private class AddRemoveFromFavoritesAsync extends AsyncTask<String, Integer, OperationResult> {

        private CardModel cardModel;

        public AddRemoveFromFavoritesAsync(CardModel cardModel) {
            this.cardModel = cardModel;
        }

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                FavoriteCard favoriteCard = favoriteCardDao.isFavCardAdded(cardModel.getCardId(), Constants.LOGGED_IN_USER);
                boolean isAddedToFav = favoriteCard != null;

                if (isAddedToFav) {
                    favoriteCardDao.delete(favoriteCard);
                    return OperationResult.success("Card successfully removed from favorites!", null);
                } else {
                    FavoriteCard cardToAdd = new FavoriteCard(
                            Constants.LOGGED_IN_USER,
                            cardModel.getCardId(),
                            cardModel.getCardName(),
                            cardModel.getPrices().getEur(),
                            cardModel.getImages().getArtCrop(),
                            cardModel.getSet(),
                            cardModel.getRarity(),
                            cardModel.getType(),
                            cardModel.getArtist()
                    );
                    favoriteCardDao.insert(cardToAdd);

                    return OperationResult.success("Card successfully added to favorites!", null);
                }
            } catch (Exception e) {
                return OperationResult.error("There was an error. Please try again later!");
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _addRemoveFavoriteResult.setValue(operationResult);
        }
    }

    private class AddToCartAsync extends AsyncTask<String, Integer, OperationResult> {

        private final CardModel card;
        private final int quantity;

        public AddToCartAsync(CardModel card, int quantity) {
            this.card = card;
            this.quantity = quantity;
        }

        @Override
        protected OperationResult doInBackground(String... strings) {
            try {
                Cart inCart = cartDao.isCardInCart(Constants.LOGGED_IN_USER, card.getCardId());

                Cart cardToAdd = new Cart(
                        Constants.LOGGED_IN_USER,
                        card.getCardId(),
                        card.getCardName(),
                        card.getPrices().getEur(),
                        quantity
                );

                if (inCart != null) {
                    cartDao.updateCart(cardToAdd);
                    return OperationResult.success("Successfully updated cart!", null);
                } else {
                    cartDao.addCardToCart(cardToAdd);
                    return OperationResult.success("Successfully added to cart!", null);
                }
            } catch (Exception e) {
                return OperationResult.error("There was an error. Please try again later!");
            }
        }

        @Override
        protected void onPostExecute(OperationResult operationResult) {
            super.onPostExecute(operationResult);
            _addRemoveFavoriteResult.setValue(operationResult);
        }
    }
}
