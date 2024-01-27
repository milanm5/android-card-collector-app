package com.example.cardproject.ui.card;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cardproject.R;
import com.example.cardproject.model.CardModel;
import com.example.cardproject.model.FavoriteCard;
import com.example.cardproject.util.Constants;
import com.example.cardproject.util.OperationResult;
import com.example.cardproject.viewmodel.CardDetailsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CardDetailsFragment extends Fragment {

    
    private CardModel card;
    
    private CardDetailsViewModel cardDetailsViewModel;

    private ImageView image;

    private TextView name, typeAndRarity, set, price, artist;

    private EditText quantityET;

    private Button addToCartBtn, decreaseQuantity, increaseQuantity;

    private FloatingActionButton favoriteBtn;

    public CardDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            card = (CardModel) getArguments().getSerializable(Constants.FAVORITE_TAG);
            setupData(card, view);
        }
        
        initViewModel();
        setupListeners();
        setupObservers();
    }

    private void setupData(CardModel card, View view) {
        image = (ImageView) view.findViewById(R.id.cardDetailsImageIV);
        name = (TextView) view.findViewById(R.id.cardDetailsNameTV);
        typeAndRarity = (TextView) view.findViewById(R.id.cardDetailsTypeAndRarityTV);
        price = (TextView) view.findViewById(R.id.cardDetailsPrice);
        artist = (TextView) view.findViewById(R.id.cardDetailsArtist);
        set = (TextView) view.findViewById(R.id.cardDetailsSetTV);

        quantityET = (EditText) view.findViewById(R.id.cardDetailsQuantityET);

        addToCartBtn = (Button) view.findViewById(R.id.cardDeatilsAddToCartBtn);
        favoriteBtn = (FloatingActionButton) view.findViewById(R.id.cardDetailsFavoriteBtn);
        decreaseQuantity = (Button) view.findViewById(R.id.cardDetailsDecrementBtn);
        increaseQuantity = (Button) view.findViewById(R.id.cardDetailsIncrementBtn);



        Glide.with(this)
                .load(card.getImages().getArtCrop())
                .centerCrop()
                .error(R.drawable.no_image)
                .into(image);

        name.setText(card.getCardName());
        typeAndRarity.setText(card.getType() + " - " + card.getRarity());
        price.setText(card.getPrices().getEur());
        artist.setText(card.getArtist());
        set.setText(card.getSet());
    }



    private void initViewModel() {
        cardDetailsViewModel = new ViewModelProvider(this).get(CardDetailsViewModel.class);
    }

    private void setupListeners() {

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDetailsViewModel.addRemoveFavorites(card);
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDetailsViewModel.addToCart(card, Integer.parseInt(quantityET.getText().toString()));
            }
        });

    }

    private void decreaseQuantity() {
        int quantity = Integer.parseInt(quantityET.getText().toString());
        if (quantity > 1) {
            quantity--;
            quantityET.setText(String.valueOf(quantity));
        }
    }

    private void increaseQuantity() {
        int quantity = Integer.parseInt(quantityET.getText().toString());
        quantity++;
        quantityET.setText(String.valueOf(quantity));
    }

    private void setupObservers() {
        cardDetailsViewModel.addRemoveFavoriteResult.observe(getViewLifecycleOwner(), new Observer<OperationResult>() {
            @Override
            public void onChanged(OperationResult operationResult) {
                switch (operationResult.status) {
                    case SUCCESS:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        cardDetailsViewModel.isResAddedToFavLiveData(card.getCardId()).observe(getViewLifecycleOwner(), new Observer<FavoriteCard>() {
            @Override
            public void onChanged(FavoriteCard favoriteCard) {
                boolean isAdded = favoriteCard != null;

                toggleFavoriteIcon(isAdded);
            }
        });
    }

    private void toggleFavoriteIcon(boolean isAdded) {
        if (isAdded) {
            favoriteBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_filled));
        } else {
            favoriteBtn.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart));
        }
    }
}