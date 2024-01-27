package com.example.cardproject.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.R;
import com.example.cardproject.model.FavoriteCard;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private List<FavoriteCard> favoriteCardList;

    private final Resources resources;

    private final FavoriteCardListener listener;
    public FavoriteAdapter(Context context, FavoriteCardListener listener) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
        this.listener = listener;
    }

    public void setFavoriteCardList(List<FavoriteCard> favoriteCardList) {
        this.favoriteCardList = favoriteCardList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (favoriteCardList != null) {
            FavoriteCard favoriteCard = favoriteCardList.get(position);

            holder.cardNameTv.setText(favoriteCard.getCardName());
            holder.cardTypeAndRarityTv.setText(favoriteCard.getType() + " - " + favoriteCard.getRarity());
            holder.cardSetTv.setText(favoriteCard.getSet());
        }
    }

    @Override
    public int getItemCount() {
        return favoriteCardList == null ? 0 : favoriteCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView cardNameTv, cardTypeAndRarityTv, cardSetTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardNameTv = (TextView) itemView.findViewById(R.id.cardNameTV);
            cardTypeAndRarityTv = (TextView) itemView.findViewById(R.id.cardTypeAndRarityTv);
            cardSetTv = (TextView) itemView.findViewById(R.id.cardSetTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(favoriteCardList.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface FavoriteCardListener {
        void onItemClicked(FavoriteCard favoriteCard);
    }
}
