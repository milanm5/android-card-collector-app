package com.example.cardproject.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.R;
import com.example.cardproject.model.CardModel;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardAdapter  extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private LayoutInflater inflater;

    private List<CardModel> cardModels;

    private final Resources resources;

    private final CardAdapterListener listener;


    public CardAdapter(Context context, CardAdapterListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.resources = context.getResources();
        this.listener = listener;
    }

    public void setCardList(List<CardModel> list) {
        this.cardModels = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {

        if (cardModels != null) {
            CardModel card = cardModels.get(position);
            holder.cardNameTv.setText(card.getCardName());
            holder.cardTypeAndRarityTv.setText(card.getType() + " - "  + card.getRarity());
            holder.cardSetTv.setText(card.getSet());
//            if (card.getImages().getArtCrop() == null) {
//                Glide.with(holder.itemView.getContext())
//                        .load(R.drawable.no_image)
//                        .centerCrop()
//                        .error(R.drawable.no_image)
//                        .into(holder.cardImageIv);
//            } else {
//                Glide.with(holder.itemView.getContext())
//                        .load(card.getImages().getArtCrop())
//                        .centerCrop()
//                        .error(R.drawable.no_image)
//                        .into(holder.cardImageIv);
//            }
        }
    }

    @Override
    public int getItemCount() {
        return cardModels == null ? 0 : cardModels.size();
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
                    listener.onItemClicked(cardModels.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface CardAdapterListener {
        void onItemClicked(CardModel cardModel);
    }

}
