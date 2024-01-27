package com.example.cardproject.ui.favorite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cardproject.R;
import com.example.cardproject.adapter.CardAdapter;
import com.example.cardproject.adapter.FavoriteAdapter;
import com.example.cardproject.model.CardImage;
import com.example.cardproject.model.CardModel;
import com.example.cardproject.model.CardPrice;
import com.example.cardproject.model.FavoriteCard;
import com.example.cardproject.util.Constants;
import com.example.cardproject.viewmodel.FavoriteViewModel;

import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteAdapter.FavoriteCardListener {


    private FavoriteViewModel favoriteViewModel;

    private RecyclerView recyclerView;

    private FavoriteAdapter adapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
        setupRecyclerView(view);
        setupObservers();
    }

    private void initViewModel() {
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
    }

    private void setupRecyclerView(View view) {
        adapter = new FavoriteAdapter(requireContext(), this);
        recyclerView = (RecyclerView) view.findViewById(R.id.favoriteCardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        favoriteViewModel.favoriteCardsLiveData.observe(getViewLifecycleOwner(), new Observer<List<FavoriteCard>>() {
            @Override
            public void onChanged(List<FavoriteCard> favoriteCards) {
                toggleRecyclerView(favoriteCards.isEmpty());
                adapter.setFavoriteCardList(favoriteCards);
            }
        });
    }

    private void toggleRecyclerView(boolean isEmpty) {
        if (isEmpty) {
            getView().findViewById(R.id.noFavoritesTV).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.favoriteCardRecyclerView).setVisibility(View.GONE);
        } else {
            getView().findViewById(R.id.noFavoritesTV).setVisibility(View.GONE);
            getView().findViewById(R.id.favoriteCardRecyclerView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(FavoriteCard favoriteCard) {
        Bundle bundle = new Bundle();
        CardImage image = new CardImage();
        image.setArtCrop(favoriteCard.getImage());
        CardPrice price = new CardPrice();
        price.setEur(favoriteCard.getPrice());
        CardModel card = new CardModel(favoriteCard.getCardId(), favoriteCard.getCardName(), image, favoriteCard.getType(), price, favoriteCard.getSet(), favoriteCard.getRarity(), favoriteCard.getArtist());
        bundle.putSerializable(Constants.FAVORITE_TAG, card);
        Navigation.findNavController(getView()).navigate(R.id.action_favoriteFragment_to_cardDetailsFragment, bundle);
    }
}