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
import com.example.cardproject.model.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    private LayoutInflater inflater;

    private List<Cart> cartList;

    private final Resources resources;

    public CartAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        resources = context.getResources();
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (cartList != null) {
            Cart cart = cartList.get(position);
            holder.cardName.setText(cart.getCardName());
            holder.cardQuantity.setText(resources.getString(R.string.quantity, String.valueOf(cart.getQuantity())));
            if (cart.getPrice() == null) {
                holder.cardPrice.setText("Price error");
            } else {
                double totalCardPrice = Double.parseDouble(cart.getPrice()) * cart.getQuantity();
                DecimalFormat df = new DecimalFormat("#.##");
                holder.cardPrice.setText(resources.getString(R.string.price, df.format(totalCardPrice)));
            }
        }
    }

    @Override
    public int getItemCount() {
        return cartList == null ? 0 : cartList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView cardName, cardQuantity, cardPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardName = itemView.findViewById(R.id.cartCardName);
            cardQuantity = itemView.findViewById(R.id.cartCardQuantity);
            cardPrice = itemView.findViewById(R.id.cartCardPrice);
        }
    }
}
